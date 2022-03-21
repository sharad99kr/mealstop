package com.dalhousie.MealStop.user.controller;

import com.dalhousie.MealStop.common.ErrorMessagesConstants;
import com.dalhousie.MealStop.common.UserMessagesConstants;
import com.dalhousie.MealStop.common.VerificationTokenConstants;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class RegistrationController {
    private static final String HTTP = "http://";
    private static final String PORT_SEPARATOR = ":";
    private static final String SAVE_PASSWORD = "/api/v1/savePassword?token=";

    @Autowired
    private IUserService IUserService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @GetMapping("/register")
    public ModelAndView showRegistrationForm(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("registration.html");
        return modelAndView;
    }

    /***
     * Registers the user model on the basis of the USER type.
     * Currently, a USER type can be customer, restaurant or ngo.
     * @param userModel contains information about the user and user type.
     * @return Response entity will return with 201 as the status if userModel is created successfully and 400 if there were any issues with the request.
     */
    @PostMapping("/register")
    public ResponseEntity<String> signUpUser(@RequestBody UserModel userModel, final HttpServletRequest request) {
        try {
            //Save the information inside database.
            User user = IUserService.signUpUser(userModel);

            //If the information of the user is saved inside the database, send a mail to the user with verification token.
            eventPublisher.publishEvent(new UserSignedUpEvent(user, getAppUrl(request)));

            return ResponseEntity.status(HttpStatus.CREATED).body(UserMessagesConstants.USER_CREATED);
        } catch (Exception e) {
            log.error(ErrorMessagesConstants.SIGNUP_USER + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(UserMessagesConstants.UNABLE_TO_ADD_USER);
        }
    }

    /**
     * Verifies the registration by checking the authenticity of the token.
     *
     * @param token registration token
     * @return status of the token.
     */
    @GetMapping("/verifyRegistration")
    public ResponseEntity<String> verifyRegistration(@RequestParam("token") String token) {
        // Gets the verification token validity result.
        // 1. If token exists in the system and is not expired valid will be returned.
        // 2. If token does not exist in the system invalid will be returned.
        // 3. If token is expired in the system expired will be returned.
        String result = IUserService.validateVerificationToken(token);
        if (result.equalsIgnoreCase(VerificationTokenConstants.VALID)) {
            return ResponseEntity.status(HttpStatus.OK).body(UserMessagesConstants.USER_VERIFIED);
        } else if (result.equalsIgnoreCase(VerificationTokenConstants.INVALID)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(UserMessagesConstants.INVALID_TOKEN);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(UserMessagesConstants.USER_EXPIRED);
        }
    }

    /**
     * Resets the password by sending a url with token within a mail.
     *
     * @param passwordModel model used for setting up new password
     * @param request incoming http request
     * @return status OK if password reset mail was sent.
     */
    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestBody PasswordModel passwordModel, final HttpServletRequest request) {
        User user = IUserService.findUserByEmail(passwordModel.getEmail());
        String url;
        if (user != null) {
            String token = UUID.randomUUID().toString();
            //Create a token to be sent with password reset mail.
            IUserService.createPasswordResetTokenForUser(user, token);
            url = passwordResetTokenMail(token, getAppUrl(request));
            return ResponseEntity.status(HttpStatus.OK).body(url);
        }
        //If no user found with the given email return bad request.
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(UserMessagesConstants.BAD_USER);
    }

    /**
     * Verifies the validity of password reset token and then, saves the password inside the system
     * @param token password reset token
     * @param passwordModel model used for setting up new password
     * @return OK if password saved successfully and Bad request if user or token was invalid.
     */
    @PostMapping("/savePassword")
    public ResponseEntity<String> savePassword(@RequestParam("token") String token, @RequestBody PasswordModel passwordModel) {
        String result = IUserService.validatePasswordResetToken(token);
        if (!result.equalsIgnoreCase(VerificationTokenConstants.VALID)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(UserMessagesConstants.INVALID_TOKEN);
        }
        Optional<User> user = IUserService.getUserByPasswordResetToken(token);
        if (user.isPresent()) {
            IUserService.changePassword(user.get(), passwordModel.getNewPassword());
            return ResponseEntity.status(HttpStatus.OK).body(UserMessagesConstants.PASSWORD_RESET_SUCCESS);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(UserMessagesConstants.BAD_USER);
        }
    }

    /**
     * Changes the password for the user when user is logged in.
     * @param passwordModel model used for setting up new password
     * @return OK if password saved successfully and Bad request if old password was invalid.
     */
    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody PasswordModel passwordModel) {
        User user = IUserService.findUserByEmail(passwordModel.getEmail());
        //Checks if old password matches.
        if (!IUserService.checkIfValidOldPassword(user, passwordModel.getOldPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(UserMessagesConstants.INVALID_OLD_PASSWORD);
        }
        IUserService.changePassword(user, passwordModel.getNewPassword());
        return ResponseEntity.status(HttpStatus.OK).body(UserMessagesConstants.PASSWORD_CHANGE_SUCCESS);
    }

    /**
     * Sends the password reset token mail
     * @param passwordResetToken
     * @param applicationUrl
     * @return
     */
    private String passwordResetTokenMail(String passwordResetToken, String applicationUrl) {
        //Send mail to the user.
        String url = applicationUrl + SAVE_PASSWORD + passwordResetToken;

        log.info("Verify url:" + url);
        return url;
    }

    /**
     * Gets the url where application is running.
     *
     * @param request http request
     * @return app url
     */
    private String getAppUrl(HttpServletRequest request) {
        return HTTP + request.getServerName() + PORT_SEPARATOR + request.getServerPort() + request.getContextPath();
    }

}
