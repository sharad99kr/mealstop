package com.dalhousie.MealStop.orders.service;

import com.dalhousie.MealStop.orders.model.Orders;

import java.util.List;

public interface IOrderService {
    public void addOrder(Orders newOrder);
    public abstract List<Orders> getAllOrders();

    public abstract List<Orders> getOrdersByCustomerID(int userId);

    public abstract Orders getOrderByOrderID(int userId);

    public abstract List<Orders> getOrdersByRestaurantID(int restaurantId);
}
