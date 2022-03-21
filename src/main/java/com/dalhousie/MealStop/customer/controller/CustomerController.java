package com.dalhousie.MealStop.customer.controller;

import com.dalhousie.MealStop.Meal.model.Meal;
import com.dalhousie.MealStop.Restaurant.model.Restaurant;
import com.dalhousie.MealStop.Restaurant.service.IRestaurantService;
import com.dalhousie.MealStop.customer.customersearch.UserSearch;
import com.dalhousie.MealStop.customer.modal.Customer;
import com.dalhousie.MealStop.customer.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class CustomerController
{
    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IRestaurantService restaurantService;

    @GetMapping("/customer/homepage")
    public String getLandingPage(Model model)
    {
        Customer customer = customerService.getCustomerDetailsFromSession();
        model.addAttribute("customer", customer);
        return "customer/landing-page";
    }

    @GetMapping("/customer/profile")
    public String getCustomerProfilePage(Model model)
    {
        Customer customer = customerService.getCustomerDetailsFromSession();
        model.addAttribute("customer", customer);
        return "customer/profile";
    }

    @GetMapping("/customer/search-restaurant")
    public String searchRestaurants(@ModelAttribute UserSearch userSearch, Model model) throws Exception
    {
        List<Restaurant> listRestaurants_Available = restaurantService.getAvailableRestaurants(userSearch.getStartDate(), userSearch.getEndDate());
        model.addAttribute("restaurants", listRestaurants_Available);
        model.addAttribute("meals", restaurantService.getRecommendedMealForCustomer(listRestaurants_Available));
        return "customer/restaurants";
    }
}
