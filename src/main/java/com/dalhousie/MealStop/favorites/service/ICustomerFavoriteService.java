package com.dalhousie.MealStop.favorites.service;

import com.dalhousie.MealStop.favorites.model.CustomerFavorites;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ICustomerFavoriteService
{
     List<CustomerFavorites> getCustomerFavorites();
     void addRestaurantToCustomerFavorites(Long restaurantId);
     void deleteCustomerFavoriteById(Long customerFavoriteId);
     int getRestaurantFavorites(Long restaurantId);
}
