package com.dalhousie.MealStop.user.controller;

import com.dalhousie.MealStop.common.CommonConstants;
import com.dalhousie.MealStop.common.RoleEnum;
import com.dalhousie.MealStop.common.UserMessagesConstants;
import com.dalhousie.MealStop.common.VerificationTokenConstants;
import com.dalhousie.MealStop.email.IEmailService;
import com.dalhousie.MealStop.user.entity.User;
import com.dalhousie.MealStop.user.event.UserSignedUpEvent;
import com.dalhousie.MealStop.user.models.PasswordModel;
import com.dalhousie.MealStop.user.models.UserModel;
import com.dalhousie.MealStop.user.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.UUID;

import static com.dalhousie.MealStop.common.CommonConstants.DATE_OF_BIRTH;
import static com.dalhousie.MealStop.common.UrlConstants.*;
import static com.dalhousie.MealStop.common.UserMessagesConstants.*;

@Controller
@RequestMapping(API_VERSION_1)
@Slf4j
public class RegistrationController implements WebMvcConfigurer {

    @Autowired
    private IUserService userService;

    @Autowired
    private IEmailService emailService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @GetMapping(REGISTER_URL)
    public String showRegistrationForm(UserModel userModel) {
        log.info(SHOW_REGISTRATION_FORM);
        return USER_REGISTER_URL;
    }

    @GetMapping(NGO_REGISTER_URL)
    public String showNgoRegistrationForm(UserModel userModel) {
        log.info(SHOW_NGO_REGISTRATION_FORM);
        return NGO_USER_REGISTER_URL;
    }

    @GetMapping(FORGOT_PASSWORD_URL)
    public String showForgotPasswordForm() {
        log.info(SHOW_FORGOT_PASSWORD_FORM);
        return USER_FORGOT_PASSWORD_URL;
    }

    @GetMapping(SAVE_PASSWORD_URL)
    public String showSavePasswordForm(@RequestParam("token") String token, PasswordModel passwordModel) {
        log.info(SHOW_FORGOT_PASSWORD_FORM);
        String result = userService.validatePasswordResetToken(token);
        if (result.equalsIgnoreCase(VerificationTokenConstants.VALID)) {
            return USER_CHANGE_PASSWORD_URL;
        } else {
            return USER_LOGIN;
        }
    }


    /***
     * Registers the user model on the basis of the USER type.
     * Currently, a USER type can be customer, restaurant or ngo.
     * @param userModel contains information about the user and user type.
     * @return Response entity will return with 201 as the status if userModel is created successfully and 400 if there were any issues with the request.
     */
    @PostMapping(value = SIGNUP_URL, consumes = {"application/json", "application/x-www-form-urlencoded"})
    public String signUpUser(@Valid UserModel userModel, BindingResult result, final HttpServletRequest request) {
        if (userModel.getRole().equals(RoleEnum.ROLE_NGO.toString())) {
            if (result.hasErrors()) {
                FieldError error = result.getFieldErrors().stream().findFirst().get();
                if (error.getField().equals(DATE_OF_BIRTH)) {
                    return NGO_USER_REGISTER_URL;
                }
            }
        } else {
            if (result.hasErrors()) {
                return USER_REGISTER_URL;
            }
        }
        try {
            User user = userService.signUpUser(userModel);

            // send a mail to the user with verification token.
            if (!user.isEnabled()) {
                eventPublisher.publishEvent(new UserSignedUpEvent(user, getAppUrl(request)));
            }

        } catch (Exception e) {
            log.error(CommonConstants.SIGNUP_USER + e.getMessage());
        }
        return USER_LOGIN;
    }

    @GetMapping(VERIFY_REGISTER_URL)
    public ResponseEntity<String> verifyRegistration(@RequestParam("token") String token) {
        // Gets the verification token validity result.
        // 1. If token exists in the system and is not expired valid will be returned.
        // 2. If token does not exist in the system invalid will be returned.
        // 3. If token is expired in the system expired will be returned.
        String result = userService.validateVerificationToken(token);
        if (result.equalsIgnoreCase(VerificationTokenConstants.VALID)) {
            return ResponseEntity.status(HttpStatus.OK).body(UserMessagesConstants.USER_VERIFIED);
        } else if (result.equalsIgnoreCase(VerificationTokenConstants.INVALID)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(UserMessagesConstants.INVALID_TOKEN);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(UserMessagesConstants.USER_EXPIRED);
        }
    }

    @PostMapping(value = FORGOT_PASSWORD_URL, consumes = {"application/json", "application/x-www-form-urlencoded"})
    public String forgotPassword(PasswordModel passwordModel, final HttpServletRequest request) {
        User user = userService.findUserByEmail(passwordModel.getEmail());
        if (user != null) {
            String token = UUID.randomUUID().toString();
            //Create a token to be sent with password reset mail.
            userService.createPasswordResetTokenForUser(user, token);
            passwordResetTokenMail(passwordModel.getEmail(), token, getAppUrl(request));
        }
        //If no user found with the given email return bad request.
        return USER_LOGIN;
    }

    @PostMapping(value = SAVE_PASSWORD_URL, consumes = {"application/json", "application/x-www-form-urlencoded"})
    public String savePassword(@Valid @RequestBody PasswordModel passwordModel, BindingResult result) {
        if (result.hasErrors()) {
            return USER_CHANGE_PASSWORD_URL;
        }
        User user = userService.findUserByEmail(passwordModel.getEmail());
        userService.changePassword(user, passwordModel.getNewpassword());
        return USER_LOGIN;
    }

    @PostMapping(value = CHANGE_PASSWORD_URL, consumes = {"application/json", "application/x-www-form-urlencoded"})
    public String changePassword(PasswordModel passwordModel) {
        User user = userService.findUserByEmail(passwordModel.getEmail());
        if (user == null)
            return USER_CHANGE_PASSWORD_URL;

        String oldPass = passwordModel.getOldpassword();
        String newPass = passwordModel.getNewpassword();
        if (oldPass.equals("") || newPass.equals(""))
            return USER_CHANGE_PASSWORD_URL;

        if (!userService.checkIfValidOldPassword(user, passwordModel.getOldpassword()))
            return USER_CHANGE_PASSWORD_URL;

        userService.changePassword(user, passwordModel.getNewpassword());
        return USER_LOGIN;
    }

    private void passwordResetTokenMail(String email, String passwordResetToken, String applicationUrl) {
        //Send mail to the user.
        String url = applicationUrl + SAVE_PASSWORD + passwordResetToken;
        emailService.sendEmail(email, url, PASSWORD_CHANGE);
        log.info("Verify url:" + url);
    }

    private String getAppUrl(HttpServletRequest request) {
        return HTTP + request.getServerName() + PORT_SEPARATOR + request.getServerPort() + request.getContextPath();
    }

}
