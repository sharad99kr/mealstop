package com.dalhousie.MealStop.customer.controller;

import com.dalhousie.MealStop.Restaurant.model.IRestaurant;
import com.dalhousie.MealStop.Restaurant.model.Restaurant;
import com.dalhousie.MealStop.Restaurant.service.RestaurantService;
import com.dalhousie.MealStop.customer.customersearch.UserSearch;
import com.dalhousie.MealStop.customer.modal.Customer;
import com.dalhousie.MealStop.customer.modal.ICustomer;
import com.dalhousie.MealStop.customer.service.ICustomerService;
import com.dalhousie.MealStop.user.model.IUser;
import com.dalhousie.MealStop.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
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
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getDetails();
        Customer customer = new Customer(user);
        model.addAttribute("customer", customer);
        return "customer/landing-page";
    }

    @GetMapping("/customer/search-restaurant")
    public String searchRestaurants(@ModelAttribute UserSearch userSearch, Model model)
    {
        List<Restaurant> restaurantList = restaurantService.getAllRestaurant();
        model.addAttribute("restaurants", restaurantList);
        return "customer/restaurants";
    }
}
