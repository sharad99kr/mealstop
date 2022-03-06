package com.dalhousie.MealStop.review.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomerReviewController
{
    @GetMapping("/customer/reviews")
    public String login(Model model)
    {
        System.err.println("customer review called");
        return "customer/reviews";
    }
}
