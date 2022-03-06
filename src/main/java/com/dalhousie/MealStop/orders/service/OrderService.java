package com.dalhousie.MealStop.orders.service;

import com.dalhousie.MealStop.orders.model.Orders;
import com.dalhousie.MealStop.orders.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class OrderService implements IOrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void addOrder(Orders orders){

    }

    @Override
    public List<Orders> getAllOrders(){

        return null;
    }

    @Override
    public List<Orders> getOrdersByUserID(int userId){

        return null;
    }

    @Override
    public Orders getOrderByOrderID(int userId){

        return null;
    }

    @Override
    public Orders getOrderByUserIDandOrderID(int userId, int orderId){

        return null;
    }

    @Override
    public List<Orders> getOrdersByRestaurantID(int restaurantId){

        return null;
    }
}
