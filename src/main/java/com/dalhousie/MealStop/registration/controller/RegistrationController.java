package com.dalhousie.MealStop.registration.controller;

import com.dalhousie.MealStop.registration.domainmodels.RegistrationRequest;
import com.dalhousie.MealStop.registration.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/registration")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED, reason = "Registration successful.")
    public ResponseEntity register(@RequestBody RegistrationRequest request){
        try{
            var registerResponse = this.registrationService.register(request);
        }catch(IllegalStateException ex){
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
