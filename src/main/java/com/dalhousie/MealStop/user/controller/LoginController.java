package com.dalhousie.MealStop.user.controller;

import com.dalhousie.MealStop.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        log.info("Trying to login.");
        return "user/login";
    }

    @GetMapping("/login-error")
    public String loginError() {
        return "user/loginerror";
    }
}
