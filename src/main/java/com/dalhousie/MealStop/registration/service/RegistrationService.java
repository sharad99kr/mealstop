package com.dalhousie.MealStop.registration.service;

import com.dalhousie.MealStop.registration.common.EmailValidator;
import com.dalhousie.MealStop.registration.domainmodels.RegistrationRequest;
import com.dalhousie.MealStop.user.domainmodels.AppUser;
import com.dalhousie.MealStop.user.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

@Service
public class RegistrationService {

    @Autowired
    private EmailValidator emailValidator;

    @Autowired
    private AppUserService appUserService;

    public String register(RegistrationRequest registrationRequest)  {
        boolean isEmailValid = emailValidator.test(registrationRequest.getEmail());

        if (!isEmailValid)
            throw new IllegalStateException("Email is not valid.");

        //appUserService.signUpUser();
        return "works";
    }
}
