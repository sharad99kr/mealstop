package com.dalhousie.MealStop.orders.service;

import com.dalhousie.MealStop.orders.model.Orders;
import com.dalhousie.MealStop.orders.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService implements IOrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void addOrder(Orders newOrder){

        System.out.println("entered to save");
        orderRepository.save(newOrder);
    }

    @Override
    public List<Orders> getAllOrders(){
        return orderRepository.findAll();
    }

    @Override
    public List<Orders> getOrdersByCustomerID(int userId){

        return orderRepository.findByCustomerId(userId);
    }

    @Override
    public Orders getOrderByOrderID(int orderId){

        return orderRepository.findById(orderId);
    }


    @Override
    public List<Orders> getOrdersByRestaurantID(int restaurantId){

        return orderRepository.findByRestaurantId(restaurantId);
    }
}
