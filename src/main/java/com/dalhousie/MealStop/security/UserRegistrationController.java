package com.dalhousie.MealStop.security;

import com.dalhousie.MealStop.user.model.User;
import com.dalhousie.MealStop.user.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Controller
public class UserRegistrationController implements WebMvcConfigurer
{
    @Autowired
    private UserService userService;

    private static final Logger log = LoggerFactory.getLogger(UserRegistrationController.class.getName());

    @PostMapping("/register")
    public ModelAndView createUser(User user)
    {
        ModelAndView modelAndView = null;
        System.err.println("new user addition"+user.toString());
        userService.addUser(user);
        return modelAndView;
    }

    @GetMapping("/register")
    public String register(User user, Model model)
    {
        System.err.println("register called");
        return "user/signup";
    }
}
