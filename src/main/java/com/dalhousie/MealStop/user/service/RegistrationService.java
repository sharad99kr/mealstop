package com.dalhousie.MealStop.user.service;

import com.dalhousie.MealStop.user.dbmodels.ConfirmationToken;
import com.dalhousie.MealStop.user.domainmodels.RegistrationRequest;
import com.dalhousie.MealStop.user.transformer.TransformRegistrationRequest;
import com.dalhousie.MealStop.user.dbmodels.ILogin;
import com.dalhousie.MealStop.user.dbmodels.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

/***
 *
 */
@Service
public class RegistrationService {

    @Autowired
    private EmailValidatorService emailValidatorService;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    /***
     * Registers the user on the basis of the USER type.
     * Currently, a user may be either customer or restaurant.
     * @param registrationRequest
     * @return
     */
    public String register(RegistrationRequest registrationRequest) {
        boolean isEmailValid = emailValidatorService.test(registrationRequest.getEmail());

        if (!isEmailValid)
          throw new IllegalStateException("Email is not valid.");

        //Transform API request format to internal format.
        IUser user = TransformRegistrationRequest.transformRegistrationRequestToUser(registrationRequest);
        ILogin login = TransformRegistrationRequest.transformRegistrationRequestToLogin(registrationRequest);


        String signUpToken = appUserService.signUpUser(user, login);

        /*String link = "http://localhost:8080/api/v1/registration/confirm?token=" + token;
        emailSender.send(
                request.getEmail(),
                buildEmail(request.getFirstName(), link));*/

        return signUpToken;
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedTime() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiredTime();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        appUserService.enableAppUser(
                confirmationToken.getUser().getEmail());
        return "confirmed";
    }
}
