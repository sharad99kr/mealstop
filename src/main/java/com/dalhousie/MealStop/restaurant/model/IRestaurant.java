package com.dalhousie.MealStop.restaurant.model;

public interface IRestaurant {

    long getId();

    void setId(long id);

    long getUserId();

    void setUserId(long userid);

    String getAvailability();

    void setAvailability(String availability);

    String getRestaurantName();

    void setRestaurantName(String restaurantName);

    String  getEmail();

    void setEmail(String email);

    String getPhoneNumber();

    void setPhoneNumber(String phoneNumber);

    String getAddress();

    void setAddress(String address);

    String getAvgReviewScore();

    void setAvgReviewScore(String reviewScore);
}
