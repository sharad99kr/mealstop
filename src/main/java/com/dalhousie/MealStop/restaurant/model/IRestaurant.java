package com.dalhousie.MealStop.restaurant.model;

public interface IRestaurant {

    public long getId();

    public void setId(long id);

    public long getUserId();

    public void setUserId(long userid);

    public String getAvailability();

    public void setAvailability(String availability);

    public String getRestaurantName();

    public void setRestaurantName(String restaurantName);

    public String  getEmail();

    public void setEmail(String email);

    public String getPhoneNumber();

    public void setPhoneNumber(String phoneNumber);

    public String getAddress();

    public void setAddress(String address);

    public String getAvgReviewScore();

    public void setAvgReviewScore(String reviewScore);

}
