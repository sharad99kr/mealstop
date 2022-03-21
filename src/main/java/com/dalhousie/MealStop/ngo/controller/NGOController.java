package com.dalhousie.MealStop.ngo.controller;

import com.dalhousie.MealStop.Restaurant.model.Restaurant;
import com.dalhousie.MealStop.Restaurant.service.RestaurantService;
import com.dalhousie.MealStop.customer.customersearch.UserSearch;
import com.dalhousie.MealStop.customer.service.ICustomerService;
import com.dalhousie.MealStop.orders.model.IOrders;
import com.dalhousie.MealStop.orders.model.Orders;
import com.dalhousie.MealStop.orders.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class NGOController
{
    @Autowired
    private IOrderService orderService;

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/ngo/homepage")
    public String getLandingPage(Model model)
    {
        List<Orders> orders= orderService.getAllCanceledOrders();
        model.addAttribute("orders", orders);
        return "ngo/landing-page";
    }

}
