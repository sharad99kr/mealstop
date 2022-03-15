package com.dalhousie.MealStop.user.controller;

import com.dalhousie.MealStop.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

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

    @GetMapping("/perform_logout")
    public String doLogout()
    {
        return "user/registration";
    }

    @GetMapping("/login-error")
    public String loginError(Model model) {
        return "user/login-error";
    }

    @GetMapping("/index")
    public String index(final HttpServletRequest request)
    {
        var authToken = SecurityContextHolder.getContext().getAuthentication();
        System.err.println("Index called");
        return "user/index";
    }
}
