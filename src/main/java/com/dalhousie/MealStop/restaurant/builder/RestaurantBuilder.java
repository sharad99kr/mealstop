package com.dalhousie.MealStop.restaurant.builder;

import com.dalhousie.MealStop.restaurant.model.Restaurant;

public class RestaurantBuilder {
    private Restaurant restaurant;

    public RestaurantBuilder()
    {
        restaurant = new Restaurant();
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurant.setRestaurantName(restaurantName);
    }

    public void setUserID(long userID) {
        this.restaurant.setUserId(userID);
    }

    public void setAvailability(String availability) {
        this.restaurant.setAvailability(availability);
    }

    public void setEmail(String email) {
        this.restaurant.setEmail(email);
    }

    public void setPhoneNumber(String phoneNumber) {
        this.restaurant.setPhoneNumber(phoneNumber);
    }

    public void setAddress(String address) {
        this.restaurant.setAddress(address);
    }

    public Restaurant createRestaurant() {
        return restaurant;
    }
}