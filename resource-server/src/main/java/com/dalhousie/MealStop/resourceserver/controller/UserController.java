package com.dalhousie.MealStop.resourceserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/api/v1/users")
    public String[] getUser(){
        return new String[]{"Udit", "Shathish"};
    }
}
