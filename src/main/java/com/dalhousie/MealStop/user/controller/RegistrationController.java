package com.dalhousie.MealStop.user.controller;

import com.dalhousie.MealStop.user.entity.User;
import com.dalhousie.MealStop.user.entity.VerificationToken;
import com.dalhousie.MealStop.user.event.UserSignedUpEvent;
import com.dalhousie.MealStop.user.models.PasswordModel;
import com.dalhousie.MealStop.user.models.UserModel;
import com.dalhousie.MealStop.user.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/v1")
@Slf4j
public class RegistrationController {

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
    public String signUpUser(@RequestBody UserModel userModel, final HttpServletRequest request) {
        User user = IUserService.signUpUser(userModel);
        eventPublisher.publishEvent(new UserSignedUpEvent(user, applicationUrl(request)));
        return "Success";
    }

    @GetMapping("/verifyRegistration")
    public String verifyRegistration(@RequestParam("token") String token) {
        String result = IUserService.validateVerificationToken(token);
        if (result.equalsIgnoreCase("valid")) {
            return "User verified.";
        }
        return "Bad User";
    }

    @GetMapping("/resendVerifyToken")
    public String resendVerificationToken(@RequestParam("token") String oldToken, final HttpServletRequest request) {
        VerificationToken verificationToken = IUserService.generateNewVerificationToken(oldToken);
        resendVerificationTokenMail(verificationToken, applicationUrl(request));
        return "Verification Link Set.";
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@RequestBody PasswordModel passwordModel, final HttpServletRequest request) {
        User user = IUserService.findUserByEmail(passwordModel.getEmail());
        String url = "";
        if (user != null) {
            String token = UUID.randomUUID().toString();
            IUserService.createPasswordResetTokenForUser(user, token);
            url = passwordResetTokenMail(token, applicationUrl(request));
        }
        return url;
    }

    @PostMapping("/savePassword")
    public String savePassword(@RequestParam("token") String token, @RequestBody PasswordModel passwordModel) {
        String result = IUserService.validatePasswordResetToken(token);
        if (!result.equalsIgnoreCase("valid")) {
            return "Invalid token.";
        }
        Optional<User> user = IUserService.getUserByPasswordResetToken(token);
        if (user.isPresent()) {
            IUserService.changePassword(user.get(), passwordModel.getNewPassword());
            return "Password reset successfully.";
        } else {
            return "Invalid token.";
        }
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestBody PasswordModel passwordModel) {
        User user = IUserService.findUserByEmail(passwordModel.getEmail());
        if (!IUserService.checkIfValidOldPassword(user, passwordModel.getOldPassword())) {
            return "Invalid old password";
        }
        IUserService.changePassword(user, passwordModel.getNewPassword());
        return "Password changed successfully.";
    }

    private String passwordResetTokenMail(String passwordResetToken, String applicationUrl) {
        //Send mail to the user.
        String url = applicationUrl + "/api/v1/savePassword?token=" + passwordResetToken;

        log.info("Verify url:" + url);
        return url;
    }

    private void resendVerificationTokenMail(VerificationToken verificationToken, String applicationUrl) {
        //Send mail to the user.
        String url = applicationUrl + "/api/v1/verifyRegistration?token=" + verificationToken.getToken();

        log.info("Verify url:" + url);
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

}
