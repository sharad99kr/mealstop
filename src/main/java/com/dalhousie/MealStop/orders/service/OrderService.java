package com.dalhousie.MealStop.orders.service;

import com.dalhousie.MealStop.orders.model.Order;
import com.dalhousie.MealStop.orders.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class OrderService implements IOrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void addOrder(Order order){

    }

    @Override
    public List<Order> getAllOrders(){

        return null;
    }

    @Override
    public List<Order> getOrdersByUserID(int userId){

        return null;
    }

    @Override
    public Order getOrderByOrderID(int userId){

        return null;
    }

    @Override
    public Order getOrderByUserIDandOrderID(int userId,int orderId){

        return null;
    }

    @Override
    public List<Order> getOrdersByRestaurantID(int restaurantId){

        return null;
    }
}
