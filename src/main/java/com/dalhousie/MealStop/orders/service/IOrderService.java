package com.dalhousie.MealStop.orders.service;

import com.dalhousie.MealStop.orders.model.Orders;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IOrderService {
    public void addOrder(Orders newOrder);
    public abstract List<Orders> getAllOrders();

    public abstract List<Orders> getOrdersByCustomerID(int userId);

    public abstract Orders getOrderByOrderID(int userId);

    public abstract List<Orders> getOrdersByRestaurantID(int restaurantId);
}
