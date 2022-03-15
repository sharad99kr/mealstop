package com.dalhousie.MealStop.user.controller;

import com.dalhousie.MealStop.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(Model model)
    {
        System.err.println("Login api called");
        return "user/login";
    }

    @PostMapping("/perform_login")
    public String doLogin(Model model)
    {
        String email = (String)model.getAttribute("username");
        String password = (String)model.getAttribute("password");
        userService.loadUserByUsername(email);
        System.err.println("doLogin api called");
        return "user/registration";
    }

    @GetMapping("/perform_logout")
    public String doLogout()
    {
        return "user/registration";
    }

    @GetMapping("/login-error")
    public String loginError(Model model) {
        return "user/login-error";
    }
}
