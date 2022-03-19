package com.dalhousie.MealStop.Restaurant.service;

import com.dalhousie.MealStop.Restaurant.model.Restaurant;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public interface IRestaurantService {
    public abstract void addRestaurant(Restaurant restaurant);
    public abstract List<Restaurant> getAllRestaurant(long id);
    public abstract List<Restaurant>  getAvailableRestaurants(Date startDate, Date endDate);
    public abstract Restaurant getRestaurantById(Long Id);
    public abstract Restaurant updateRestaurant(Restaurant restaurant, long id);
}
