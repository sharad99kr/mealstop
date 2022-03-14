package com.dalhousie.MealStop.orders.service;
import com.dalhousie.MealStop.orders.model.Orders;
import com.dalhousie.MealStop.orders.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dalhousie.MealStop.orders.Constants.Constants;

import java.util.List;

public class OrderService implements IOrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void addOrder(Orders newOrder){

        orderRepository.save(newOrder);
    }

    @Override
    public List<Orders> getAllOrders(){
        return orderRepository.findAll();
    }

    @Override
    public List<Orders> getAllCanceledOrders(){

        return orderRepository.findByStatus(Constants.CANCELLED);
    }

    @Override
    public List<Orders> getOrdersByCustomerID(long userId){

        return orderRepository.findByCustomerId(userId);
    }

    @Override
    public Orders getOrderByOrderID(long orderId){

        return orderRepository.findById(orderId);
    }


    @Override
    public List<Orders> getOrdersByRestaurantID(long restaurantId){

        return orderRepository.findByRestaurantId(restaurantId);
    }

    //method to return most ordered meal by restaurant id and customer id.
    // It returns list of meal ids
    public List<Long> getMostOrderedMeal(long customerId, long restaurantId){
        return orderRepository.findByCustomerIdAndRestaurantId(customerId,restaurantId);
    }

    //method to return most ordered meal by restaurant
    // It returns list of meal ids
    public List<Long> getMostOrderedMealOfRestaurant(long restaurantId){
        return orderRepository.findAllByRestaurantId(restaurantId);
    }

    //method to return most ordered meal by customer id
    // It returns list of meal ids
    public List<Long> getMostOrderedMealOfCustomer(long customerId){
        return orderRepository.findAllByCustomerId(customerId);
    }
}
