package com.dalhousie.MealStop.Restaurant.model;

public interface IRestaurant {

    public long getId();

    public String getRestaurantName();

    public void setRestaurantName(String restaurantName);

    public String getRestaurantUsername();

    public void setRestaurantUsername(String restaurantUsername);

    public String getPassword();

    public void setPassword(String password);

    public String  getEmail();

    public void setEmail(String email);

    public String getPhoneNumber();

    public void setPhoneNumber(String mobileNumber);

    public String getAddress();

    public void setAddress(String address);

    public Integer getAccountStatus();

    public void setAccountStatus(Integer accountStatus);

}
