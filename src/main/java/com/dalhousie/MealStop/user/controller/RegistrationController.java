package com.dalhousie.MealStop.user.controller;

import com.dalhousie.MealStop.user.domainmodels.RegistrationRequest;
import com.dalhousie.MealStop.user.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/register")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    /***
     * Registers the user on the basis of the USER type.
     * Currently, a user may be either customer or restaurant.
     * @param request Registration request contains information about the user and user type.
     * @return Response entity will return with 201 as the status if user is created successfully and 400 if there were any issues with the request.
     */
    @PostMapping
    public ResponseEntity registerUser(@RequestBody RegistrationRequest request) {
        String registerResponse;
        try {
            registerResponse = this.registrationService.register(request);
            System.out.println(registerResponse);
        } catch (IllegalStateException ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(registerResponse, HttpStatus.CREATED);
    }

    @GetMapping(path = "confirm")
    public String confirmToken(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }

}
