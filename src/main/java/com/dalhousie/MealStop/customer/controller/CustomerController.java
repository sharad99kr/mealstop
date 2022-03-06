package com.dalhousie.MealStop.customer.controller;

import com.dalhousie.MealStop.Restaurant.model.IRestaurant;
import com.dalhousie.MealStop.Restaurant.model.Restaurant;
import com.dalhousie.MealStop.Restaurant.service.RestaurantService;
import com.dalhousie.MealStop.customer.customersearch.UserSearch;
import com.dalhousie.MealStop.customer.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CustomerController
{
    @Autowired
    private ICustomerService customerService;

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/customer/homepage")
    public String getLandingPage(Model model)
    {
        return "customer/landing-page";
    }

    @GetMapping("/customer/search-restaurant")
    public String searchRestaurants(@ModelAttribute UserSearch userSearch, Model model)
    {
        List<Restaurant> restaurantList = restaurantService.getAllRestaurant();
        System.err.println(restaurantList);
        model.addAttribute("restaurants", restaurantList);
        return "customer/restaurants";
    }
}
