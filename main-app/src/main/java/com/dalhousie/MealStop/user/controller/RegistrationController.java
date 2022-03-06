package com.dalhousie.MealStop.user.controller;

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

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/v1")
@Slf4j
public class RegistrationController {
    private static final String HTTP = "http://";
    private static final String PORT_SEPARATOR = ":";
    private static final String SAVE_PASSWORD =  "/api/v1/savePassword?token=";
    @Autowired
    private IUserService IUserService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    /***
     * Registers the userModel on the basis of the USER type.
     * Currently, a userModel may be either customer or restaurant.
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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(UserMessagesConstants.UNABLE_TO_ADD_USER);
        }
    }

    @GetMapping("/verifyRegistration")
    public ResponseEntity<String> verifyRegistration(@RequestParam("token") String token) {
        String result = IUserService.validateVerificationToken(token);
        if (result.equalsIgnoreCase(VerificationTokenConstants.VALID)) {
            return ResponseEntity.status(HttpStatus.OK).body(UserMessagesConstants.USER_VERIFIED);
        } else if (result.equalsIgnoreCase(VerificationTokenConstants.INVALID)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(UserMessagesConstants.INVALID_TOKEN);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(UserMessagesConstants.USER_EXPIRED);
        }
    }

    /*@GetMapping("/resendVerifyToken")
    public String resendVerificationToken(@RequestParam("token") String oldToken, final HttpServletRequest request) {
        VerificationToken verificationToken = IUserService.generateNewVerificationToken(oldToken);
        resendVerificationTokenMail(verificationToken, getAppUrl(request));
        return "Verification Link Sent.";
    }*/

    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestBody PasswordModel passwordModel, final HttpServletRequest request) {
        User user = IUserService.findUserByEmail(passwordModel.getEmail());
        String url;
        if (user != null) {
            String token = UUID.randomUUID().toString();
            IUserService.createPasswordResetTokenForUser(user, token);
            url = passwordResetTokenMail(token, getAppUrl(request));
            return ResponseEntity.status(HttpStatus.OK).body(url);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(UserMessagesConstants.BAD_USER);
    }

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

    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody PasswordModel passwordModel) {
        User user = IUserService.findUserByEmail(passwordModel.getEmail());
        if (!IUserService.checkIfValidOldPassword(user, passwordModel.getOldPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(UserMessagesConstants.INVALID_OLD_PASSWORD);
        }
        IUserService.changePassword(user, passwordModel.getNewPassword());
        return ResponseEntity.status(HttpStatus.OK).body(UserMessagesConstants.PASSWORD_CHANGE_SUCCESS);
    }

    private String passwordResetTokenMail(String passwordResetToken, String applicationUrl) {
        //Send mail to the user.
        String url = applicationUrl + SAVE_PASSWORD + passwordResetToken;

        log.info("Verify url:" + url);
        return url;
    }

    /*private void resendVerificationTokenMail(VerificationToken verificationToken, String applicationUrl) {
        //Send mail to the user.
        String url = applicationUrl + "/api/v1/verifyRegistration?token=" + verificationToken.getToken();

        log.info("Verify url:" + url);
    }*/

    private String getAppUrl(HttpServletRequest request) {
        return HTTP + request.getServerName() + PORT_SEPARATOR + request.getServerPort() + request.getContextPath();
    }

}
