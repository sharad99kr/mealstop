package com.dalhousie.MealStop.Restaurant.service;

import com.dalhousie.MealStop.Restaurant.model.Restaurant;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface IRestaurantService {
    public abstract void addRestaurant(Restaurant restaurant);
    public abstract List<Restaurant> getAllRestaurant();
    public abstract void updateRestaurant(Long id, Restaurant restaurant);
    public abstract List<Restaurant>  getAvailableRestaurants(Date startDate, Date endDate);
}
