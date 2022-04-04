package com.dalhousie.MealStop.review.controller;

import com.dalhousie.MealStop.restaurant.model.Restaurant;
import com.dalhousie.MealStop.restaurant.service.IRestaurantService;
import com.dalhousie.MealStop.customer.model.Customer;
import com.dalhousie.MealStop.customer.service.ICustomerService;
import com.dalhousie.MealStop.review.model.CustomerReview;
import com.dalhousie.MealStop.review.service.ICustomerReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CustomerReviewController
{
    @Autowired
    ICustomerReviewService customerReviewService;

    @Autowired
    ICustomerService customerService;

    @Autowired
    IRestaurantService restaurantService;

    @GetMapping("/customer/reviews")
    public String getReviewPage(Model model)
    {
        Customer customer = customerService.getCustomerDetailsFromSession();
        List<CustomerReview> reviewList = customerReviewService.getReviewsOfCustomer(customer);
        model.addAttribute("reviews", reviewList);
        return "customer/reviews";
    }

    @GetMapping("/customer/add_review/{id}")
    public String getAddReviewPage(@PathVariable("id") long restaurantId, Model model)
    {
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        model.addAttribute("restaurant", restaurant);
        return "reviews/add-review";
    }

    @GetMapping("/customer/update_review/{id}")
    public String getUpdateReviewPage(@PathVariable("id") long reviewId, Model model)
    {
        CustomerReview customerReview = customerReviewService.getReviewById(reviewId);
        model.addAttribute("review", customerReview);
        return "reviews/edit-review";
    }

    @PostMapping("/customer/add_review/{id}")
    public String addReview(@ModelAttribute CustomerReview review, @PathVariable("id") long restaurantId)
    {
        Customer customer = customerService.getCustomerDetailsFromSession();
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        review.setCustomer(customer);
        review.setRestaurant(restaurant);
        customerReviewService.addReview(review);
        return "redirect:/customer/reviews";
    }

    @PostMapping("/customer/update_review/{id}")
    public String updateReview(@PathVariable("id") long reviewId, @ModelAttribute CustomerReview customerReview)
    {
        customerReview.setId(reviewId);
        customerReviewService.updateReview(reviewId, customerReview);
        return "redirect:/customer/reviews";
    }


    @PostMapping("/customer/delete_review/{id}")
    public String deleteReview(@PathVariable("id") long reviewId)
    {
        customerReviewService.deleteReviewById(reviewId);
        return "redirect:/customer/reviews";
    }
}
