package com.dalhousie.MealStop.orders.model;

public class OrdersPayload{
    //this class is used to send data to frontend/html pages
    public long orderId;
    public String mealName;
    public String date;
    public float amount;
    public String status;
    public String restaurantName;
    public String imageUrl;
}