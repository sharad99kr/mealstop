package com.dalhousie.MealStop.restaurant.service;

import com.dalhousie.MealStop.meal.model.Meal;
import com.dalhousie.MealStop.restaurant.model.Restaurant;
import com.dalhousie.MealStop.user.entity.User;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface IRestaurantService {
    void addRestaurant(Restaurant restaurant);
    List<Restaurant> getAllRestaurantByUserId();
    List<Restaurant>  getAvailableRestaurants(Date startDate, Date endDate) throws Exception;
    Restaurant getRestaurantById(Long Id);
    Restaurant updateRestaurant(Restaurant restaurant, long id);
    List<Meal> getRecommendedMealForCustomer(List<Restaurant> availableRestaurants);
    User getRestaurantUserDetailsFromSession();
    List<String> getRestaurantReviews(long id);
}
