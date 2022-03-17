package com.dalhousie.MealStop.favorites.service;

import com.dalhousie.MealStop.favorites.modal.CustomerFavorites;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerFavoriteService
{
    public List<CustomerFavorites> getCustomerFavorites();
    public void addRestaurantToCustomerFavorites(Long restaurantId);
    public void deleteCustomerFavoriteById(Long customerFavoriteId);
}
