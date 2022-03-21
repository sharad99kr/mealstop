package com.dalhousie.MealStop.Restaurant.controller;

import com.dalhousie.MealStop.Meal.model.Meal;
import com.dalhousie.MealStop.Recommendation.service.IRecommendationService;
import com.dalhousie.MealStop.Restaurant.model.Restaurant;
import com.dalhousie.MealStop.Restaurant.service.IRestaurantService;
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

    @GetMapping("/get_restaurant/{id}")
    public String getAllRestaurants(Model model, @PathVariable("id") long id)
    {
        List<Restaurant> listRestaurants = restaurantService.getAllRestaurantByUserId(id);
        model.addAttribute("restaurants_list", listRestaurants);

        return "restaurant/get_restaurant";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") long id, Model model)
    {
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        model.addAttribute("restaurant", restaurant);
        return "restaurant/update_restaurant";
    }

    @PostMapping("/update_restaurant/{id}")
    public String updateRestaurant(@ModelAttribute Restaurant restaurant, @PathVariable("id") long id)
    {
        Restaurant updatedRestaurant = restaurantService.updateRestaurant(restaurant, id);
        return "redirect:/get_restaurant/" + updatedRestaurant.getUserId();
    }

    @GetMapping("/add_restaurant_form")
    public String addRestaurantForm()
    {
        return "restaurant/add_restaurant";
    }

    @PostMapping("/add_restaurant")
    public String addRestaurant(@ModelAttribute Restaurant restaurant)
    {
        //get user from session manager
        restaurantService.addRestaurant(restaurant);
        return "redirect:/get_restaurant/" + restaurant.getUserId();
    }

    @GetMapping("/get_restaurants_available")
    public String getRestaurantAvailabile(@RequestParam(value = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                          @RequestParam(value = "endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate, Model model) throws Exception {
        List<Restaurant> listRestaurants_Available = restaurantService.getAvailableRestaurants(startDate, endDate);
        model.addAttribute("restaurants", listRestaurants_Available);
        model.addAttribute("meals", restaurantService.getRecommendedMealForCustomer(listRestaurants_Available));
        return "customer/restaurants";
    }
}
