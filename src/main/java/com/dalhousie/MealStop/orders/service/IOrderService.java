package com.dalhousie.MealStop.orders.service;

import com.dalhousie.MealStop.orders.model.Order;

import java.util.List;

public interface IOrderService {
    public abstract void addOrder(Order order);
    public abstract List<Order> getAllOrders();

    public abstract List<Order> getOrdersByUserID(int userId);

    public abstract Order getOrderByOrderID(int userId);

    public abstract Order getOrderByUserIDandOrderID(int userId,int orderId);

    public abstract List<Order> getOrdersByRestaurantID(int restaurantId);
}
