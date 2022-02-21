package com.dalhousie.MealStop.registration.service;

import com.dalhousie.MealStop.registration.common.EmailValidator;
import com.dalhousie.MealStop.registration.domainmodels.RegistrationRequest;
import com.dalhousie.MealStop.registration.transformer.TransformRegistrationRequest;
import com.dalhousie.MealStop.user.dbmodels.ILogin;
import com.dalhousie.MealStop.user.dbmodels.IUser;
import com.dalhousie.MealStop.user.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    @Autowired
    private EmailValidator emailValidator;

    @Autowired
    private AppUserService appUserService;

    public boolean register(RegistrationRequest registrationRequest)  {
        boolean isEmailValid = emailValidator.test(registrationRequest.getEmail());

        //if (!isEmailValid)
          //  throw new IllegalStateException("Email is not valid.");

        IUser user = TransformRegistrationRequest.transformRegistrationRequestToUser(registrationRequest);
        ILogin login = TransformRegistrationRequest.transformRegistrationRequestToLogin(registrationRequest);
        System.out.println(appUserService.signUpUser(user, login));
        return true;
    }
}
