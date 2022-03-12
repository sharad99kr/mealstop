package com.dalhousie.MealStop.review.controller;

import com.dalhousie.MealStop.customer.modal.Customer;
import com.dalhousie.MealStop.customer.modal.ICustomer;
import com.dalhousie.MealStop.customer.service.ICustomerService;
import com.dalhousie.MealStop.review.modal.CustomerReview;
import com.dalhousie.MealStop.review.service.CustomerReviewService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String getReviewPage(@RequestParam(value = "id") String customerId, Model model)
    {
        Customer customer = customerService.getCustomerById(customerId);
        List<CustomerReview> reviewList = customerReviewService.getReviewsOfCustomer(customer);
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
