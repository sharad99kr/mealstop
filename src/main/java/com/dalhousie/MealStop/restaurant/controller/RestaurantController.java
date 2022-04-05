package com.dalhousie.MealStop.restaurant.controller;

import com.dalhousie.MealStop.customer.model.UserSearch;
import com.dalhousie.MealStop.favorites.service.ICustomerFavoriteService;
import com.dalhousie.MealStop.restaurant.model.Restaurant;
import com.dalhousie.MealStop.restaurant.service.IRestaurantService;
import com.dalhousie.MealStop.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class RestaurantController
{
    @Autowired
    private IRestaurantService restaurantService;

    @Autowired
    private ICustomerFavoriteService customerFavoriteService;

    @GetMapping("/restaurant/get_restaurant")
    public String getAllRestaurants(Model model)
    {
        List<Restaurant> listRestaurants = restaurantService.getAllRestaurantByUserId();
        Map<Restaurant, String> restaurantToReviewScoreMap = new HashMap<>();
        for(Restaurant restaurant : listRestaurants)
        {
            restaurantToReviewScoreMap.put(restaurant, restaurant.getAvgReviewScore());
        }
        model.addAttribute("restaurants_list", restaurantToReviewScoreMap);

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
        restaurantService.updateRestaurant(restaurant, id);
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
        model.addAttribute("likes", customerFavoriteService.getRestaurantFavorites(id));
        return "restaurant/reviews";
    }

    @GetMapping("/customer/search-restaurant")
    public String searchRestaurants(@ModelAttribute UserSearch userSearch, Model model) throws Exception
    {
        Date startDate = userSearch.getStartDate();
        Date endDate = userSearch.getEndDate();
        List<Restaurant> availableRestaurants = restaurantService.getAvailableRestaurants(startDate, endDate);
        model.addAttribute("restaurants", availableRestaurants);
        model.addAttribute("meals", restaurantService.getRecommendedMealForCustomer(availableRestaurants));
        return "customer/restaurants";
    }
}
