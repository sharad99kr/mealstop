package com.dalhousie.MealStop.favorites.model;

import com.dalhousie.MealStop.customer.model.Customer;
import com.dalhousie.MealStop.restaurant.model.Restaurant;

public interface ICustomerFavorites {
    long getId();

    void setId(long id);

    Customer getCustomer();

    void setCustomer(Customer customer);

    Restaurant getRestaurant();

    void setRestaurant(Restaurant restaurant);
}
