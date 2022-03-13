package com.dalhousie.MealStop.Restaurant.model;

public interface IRestaurant {

    public long getId();

    public long getUserId();

    public void setUserId(long userid);

    public String getAvailability();

    public void setAvailability(String availability);

    public String getRestaurantName();

    public void setRestaurantName(String restaurantName);

    public String  getEmail();

    public void setEmail(String email);

    public String getPhoneNumber();

    public void setPhoneNumber(String mobileNumber);

    public String getAddress();

    public void setAddress(String address);

}
