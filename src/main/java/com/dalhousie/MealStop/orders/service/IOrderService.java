package com.dalhousie.MealStop.orders.service;

import com.dalhousie.MealStop.orders.model.Orders;

import java.util.List;

public interface IOrderService {
    public abstract void addOrder(Orders orders);
    public abstract List<Orders> getAllOrders();

    public abstract List<Orders> getOrdersByUserID(int userId);

    public abstract Orders getOrderByOrderID(int userId);

    public abstract Orders getOrderByUserIDandOrderID(int userId, int orderId);

    public abstract List<Orders> getOrdersByRestaurantID(int restaurantId);
}
