package com.dalhousie.MealStop.review.controller;

import com.dalhousie.MealStop.customer.modal.Customer;
import com.dalhousie.MealStop.customer.modal.ICustomer;
import com.dalhousie.MealStop.customer.service.ICustomerService;
import com.dalhousie.MealStop.review.modal.CustomerReview;
import com.dalhousie.MealStop.review.service.CustomerReviewService;
import com.dalhousie.MealStop.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CustomerReviewController
{
    @Autowired
    CustomerReviewService customerReviewService;

    @Autowired
    ICustomerService customerService;

    @GetMapping("/customer/reviews")
    public String getReviewPage(Model model)
    {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getDetails();
        String userId = String.valueOf(user.getId());
        Customer customer = customerService.getCustomerById(userId);

        List<CustomerReview> reviewList = customerReviewService.getReviewsOfCustomer(customer);
        System.err.println("reviews: "+ reviewList);
        model.addAttribute("reviews", reviewList);
        return "customer/reviews";
    }

    @PostMapping("/customer/add_review")
    public String addReview(@ModelAttribute("course") CustomerReview review, Model model)
    {
        customerReviewService.addReview(review);
        return "course/reviews";
    }
}
