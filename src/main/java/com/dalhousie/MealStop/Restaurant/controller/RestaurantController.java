package com.dalhousie.MealStop.Restaurant.controller;

import com.dalhousie.MealStop.Meal.model.Meal;
import com.dalhousie.MealStop.Restaurant.repository.RestaurantRepository;
import com.dalhousie.MealStop.Restaurant.model.Restaurant;

import com.dalhousie.MealStop.Restaurant.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class RestaurantController
{
    @Autowired
    private RestaurantService restaurantService;

    @PostMapping("/add_restaurant")
    public String addRestaurant(@RequestBody Restaurant restaurant, Model model)
    {
        restaurantService.addRestaurant(restaurant);
        model.addAttribute("restaurant", restaurant);
        return "restaurant/add_restaurant";
    }

    @GetMapping("/get_restaurant")
    public String getAllRestaurants(Model model)
    {
        List<Restaurant> listRestaurants = restaurantService.getAllRestaurant();
        model.addAttribute("restaurants_list", listRestaurants);
        return "restaurant/get_restaurant";
    }

    @PutMapping("/update_restaurant/{id}")
    public String updateRestaurant(@PathVariable("id") long id, @RequestBody Restaurant restaurant, Model model)
    {
        restaurantService.updateRestaurant(id, restaurant);
        model.addAttribute("updateRestaurant", restaurant);
        return "restaurant/update_restaurant";
    }
}
