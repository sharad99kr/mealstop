package com.dalhousie.MealStop.favorites.service;

import com.dalhousie.MealStop.favorites.model.CustomerFavorites;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ICustomerFavoriteService
{
    public List<CustomerFavorites> getCustomerFavorites();
    public void addRestaurantToCustomerFavorites(Long restaurantId);
    public void deleteCustomerFavoriteById(Long customerFavoriteId);
    public int getRestaurantFavorites(Long restaurantId);
}
