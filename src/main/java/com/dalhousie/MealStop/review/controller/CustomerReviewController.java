package com.dalhousie.MealStop.review.controller;

import com.dalhousie.MealStop.Restaurant.model.Restaurant;
import com.dalhousie.MealStop.Restaurant.service.IRestaurantService;
import com.dalhousie.MealStop.customer.modal.Customer;
import com.dalhousie.MealStop.customer.service.ICustomerService;
import com.dalhousie.MealStop.review.modal.CustomerReview;
import com.dalhousie.MealStop.review.service.CustomerReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CustomerReviewController
{
    @Autowired
    CustomerReviewService customerReviewService;

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
        System.err.println("editing review "+customerReview);
        model.addAttribute("review", customerReview);
        return "reviews/edit-review";
    }

    @PostMapping("/customer/add_review/{id}")
    public String addReview(@ModelAttribute CustomerReview review, @PathVariable("id") long restaurantId)
    {
        Customer customer = customerService.getCustomerDetailsFromSession();
        System.err.println("customer information="+customer.getId());
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);

        review.setCustomer(customer);
        review.setRestaurant(restaurant);

        System.err.println(review);
        customerReviewService.addReview(review);
        return "redirect:/customer/reviews";
    }

    @PostMapping("/customer/update_review/{id}")
    public String updateReview(@PathVariable("id") long reviewId, @ModelAttribute CustomerReview customerReview)
    {
        System.err.println("customer review param is "+ customerReview);
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
