package com.dalhousie.MealStop.restaurant.controller;

import com.dalhousie.MealStop.recommendation.service.IRecommendationService;
import com.dalhousie.MealStop.restaurant.model.Restaurant;
import com.dalhousie.MealStop.restaurant.service.IRestaurantService;
import com.dalhousie.MealStop.review.modal.CustomerReview;
import com.dalhousie.MealStop.review.service.ICustomerReviewService;
import com.dalhousie.MealStop.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class RestaurantController
{
    @Autowired
    private IRestaurantService restaurantService;

    @Autowired
    private IRecommendationService recommendationService;

    @Autowired
    private ICustomerReviewService customerReviewService;

    @GetMapping("/restaurant/get_restaurant")
    public String getAllRestaurants(Model model)
    {
        List<Restaurant> listRestaurants = restaurantService.getAllRestaurantByUserId();
        model.addAttribute("restaurants_list", listRestaurants);

        return "restaurant/get_restaurant";
    }

    @GetMapping("/restaurant/edit/{id}")
    public String edit(@PathVariable("id") long id, Model model)
    {
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        model.addAttribute("restaurant", restaurant);
        return "restaurant/update_restaurant";
    }

    @PostMapping("/restaurant/update_restaurant/{id}")
    public String updateRestaurant(@Valid Restaurant restaurant, BindingResult result, @PathVariable("id") long id)
    {
        if (result.hasErrors()) {
            return "restaurant/update_restaurant";
        }
        Restaurant updatedRestaurant = restaurantService.updateRestaurant(restaurant, id);
        return "redirect:/restaurant/get_restaurant/";
    }

    @GetMapping("/restaurant/add_restaurant_form")
    public String addRestaurantForm(Restaurant restaurant)
    {
        return "restaurant/add_restaurant";
    }

    @PostMapping("/restaurant/add_restaurant")
    public String addRestaurant(@Valid Restaurant restaurant, BindingResult result)
    {
        if (result.hasErrors()) {
            return "restaurant/add_restaurant";
        }
        restaurantService.addRestaurant(restaurant);
        return "redirect:/restaurant/get_restaurant/";
    }

    @GetMapping("/restaurant/profile")
    public String getRestaurantProfilePage(Model model)
    {
        User user = restaurantService.getRestaurantUserDetailsFromSession();
        model.addAttribute("user", user);
        return "restaurant/profile";
    }

    @GetMapping("/restaurant/reviews/{id}")
    public String getRestaurantReviews(@PathVariable("id") long id,Model model)
    {
        List<String> customerReview = restaurantService.getRestaurantReviews(id);
        model.addAttribute("customerReview", customerReview);

        return "restaurant/reviews";
    }
}
