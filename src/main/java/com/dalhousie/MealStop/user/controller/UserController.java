package com.dalhousie.MealStop.user.controller;

import com.dalhousie.MealStop.user.repository.UserRepository;
import com.dalhousie.MealStop.user.model.User;

import com.dalhousie.MealStop.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserController
{
    @Autowired
    private UserService userService;

    @PostMapping("/add_user")
    public String addUser(@RequestBody User user, Model model)
    {
        userService.addUser(user);
        model.addAttribute("user", user);
        return "user/add_user";
    }

    @GetMapping("/get_user")
    public String getAllUsers(Model model)
    {
        List<User> listCustomers = userService.getAllUser();
        model.addAttribute("users_list", listCustomers);
        System.err.println(listCustomers);
        return "user/get_user";
    }
}


