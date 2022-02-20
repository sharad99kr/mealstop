package com.dalhousie.MealStop.registration.service;

import com.dalhousie.MealStop.registration.domainmodels.RegistrationRequest;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    public String register(RegistrationRequest registrationRequest){
        return "this works";
    }
}
