package com.dalhousie.MealStop.Restaurant.service;

import com.dalhousie.MealStop.Meal.model.Meal;
import com.dalhousie.MealStop.Restaurant.model.Restaurant;
import com.dalhousie.MealStop.Restaurant.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class RestaurantServiceImplementation implements RestaurantService {
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public void addRestaurant(Restaurant restaurant)
    {
        restaurantRepository.save(restaurant);
    }

    @Override
    public List<Restaurant> getAllRestaurant()
    {
        List<Restaurant> restaurantList = restaurantRepository.findAll();
        return restaurantList;
    }

    @Override
    public void updateRestaurant(Long id, Restaurant restaurant)
    {
        Optional<Restaurant> restaurantData = restaurantRepository.findById(id);
        if(restaurantData.isPresent())
        {
            Restaurant _restaurant = restaurantData.get();
            _restaurant.setRestaurantName(restaurant.getRestaurantName());
            _restaurant.setAddress(restaurant.getAddress());
            _restaurant.setEmail(restaurant.getEmail());
            _restaurant.setAccountStatus(restaurant.getAccountStatus());
            _restaurant.setPhoneNumber(restaurant.getPhoneNumber());
            restaurantRepository.save(_restaurant);
        }
    }
}
