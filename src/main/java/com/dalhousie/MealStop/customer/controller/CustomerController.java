package com.dalhousie.MealStop.customer.controller;

import com.dalhousie.MealStop.Reward.service.IRewardService;
import com.dalhousie.MealStop.restaurant.model.Restaurant;
import com.dalhousie.MealStop.restaurant.service.IRestaurantService;
import com.dalhousie.MealStop.customer.customersearch.UserSearch;
import com.dalhousie.MealStop.customer.model.Customer;
import com.dalhousie.MealStop.customer.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Date;
import java.util.List;

@Controller
public class CustomerController
{
    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IRestaurantService restaurantService;

    @Autowired
    private IRewardService rewardService;

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
        boolean isCustomerRewardsPresent = rewardService.isRewardPointsRedeemable(customer.getId());
        model.addAttribute("customer", customer);
        model.addAttribute("isCustomerRewardsPresent", isCustomerRewardsPresent);
        return "customer/profile";
    }
}
