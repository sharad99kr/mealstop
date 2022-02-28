package com.dalhousie.MealStop.Restaurant.service;

import com.dalhousie.MealStop.Restaurant.model.Restaurant;

import java.util.List;

public interface RestaurantService {
    public abstract void addRestaurant(Restaurant restaurant);
    public abstract List<Restaurant> getAllRestaurant();
    public abstract void updateRestaurant(Long id, Restaurant restaurant);
}
