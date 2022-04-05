package com.dalhousie.MealStop.orders.model;

import java.sql.Date;

public interface IOrders {

     long getOrderId();

     int getOrderStatus();

     void setOrderStatus(int status);

     long getCustomerId();

     long getRestaurantId();

     long getMealId();

     void setCustomerId(long customerId);

     void setRestaurantId(long restaurantId);

     void setMealId(long mealId);

     float getOrderAmount();

     void setOrderAmount(int amount);

     Date getOrderTime();

     void setOrderTime();

}
