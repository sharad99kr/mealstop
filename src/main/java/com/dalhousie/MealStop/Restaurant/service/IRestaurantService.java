package com.dalhousie.MealStop.Restaurant.service;

import com.dalhousie.MealStop.Meal.model.Meal;
import com.dalhousie.MealStop.Restaurant.model.Restaurant;
import com.dalhousie.MealStop.user.entity.User;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public interface IRestaurantService {
    public abstract void addRestaurant(Restaurant restaurant);
    public abstract List<Restaurant> getAllRestaurantByUserId();
    public abstract List<Restaurant>  getAvailableRestaurants(Date startDate, Date endDate) throws Exception;
    public abstract Restaurant getRestaurantById(Long Id);
    public abstract Restaurant updateRestaurant(Restaurant restaurant, long id);
    public abstract List<Meal> getRecommendedMealForCustomer(List<Restaurant> availableRestaurants);
    public abstract User getRestaurantUserDetailsFromSession();
}
