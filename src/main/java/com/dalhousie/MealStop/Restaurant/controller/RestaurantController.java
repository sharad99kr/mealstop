package com.dalhousie.MealStop.Restaurant.controller;

import com.dalhousie.MealStop.Meal.model.Meal;
import com.dalhousie.MealStop.Recommendation.service.IRecommendationService;
import com.dalhousie.MealStop.Restaurant.model.Restaurant;
import com.dalhousie.MealStop.Restaurant.service.IRestaurantService;
import com.dalhousie.MealStop.customer.modal.Customer;
import com.dalhousie.MealStop.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class RestaurantController
{
    @Autowired
    private IRestaurantService restaurantService;

    @Autowired
    private IRecommendationService recommendationService;

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
    public String updateRestaurant(@ModelAttribute Restaurant restaurant, @PathVariable("id") long id)
    {
        Restaurant updatedRestaurant = restaurantService.updateRestaurant(restaurant, id);
        return "redirect:/restaurant/get_restaurant/";
    }

    @GetMapping("/restaurant/add_restaurant_form")
    public String addRestaurantForm()
    {
        return "restaurant/add_restaurant";
    }

    @PostMapping("/restaurant/add_restaurant")
    public String addRestaurant(@ModelAttribute Restaurant restaurant)
    {
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
}
