package com.dalhousie.MealStop.orders.model;

public interface IOrder {

    public long getOrderId();

    public long getCustomerId();

    public long getRestaurantId();

    public long getMealId();

    public long getPaymentId();




    public void setCustomerId(long customerId);

    public void setRestaurantId(long restaurantId);

    public void setMealId(long mealId);

    public void setPaymentId(long paymentId);

}
