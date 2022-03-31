package com.dalhousie.MealStop.orders.model;

import java.sql.Date;

public interface IOrders {

    public long getOrderId();

    public int getOrderStatus();

    public void setOrderStatus(int status);

    public long getCustomerId();

    public long getRestaurantId();


    public long getMealId();

    public long getPaymentId();

    public void setCustomerId(long customerId);

    public void setRestaurantId(long restaurantId);

    public void setMealId(long mealId);

    public void setPaymentId(long paymentId);

    public float getOrderAmount();

    public void setOrderAmount(int amount);

    public Date getOrderTime();

    public void setOrderTime();

}
