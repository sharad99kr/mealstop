package com.dalhousie.MealStop.restaurant.service;

import com.dalhousie.MealStop.meal.model.Meal;
import com.dalhousie.MealStop.restaurant.model.Restaurant;
import com.dalhousie.MealStop.user.entity.User;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
