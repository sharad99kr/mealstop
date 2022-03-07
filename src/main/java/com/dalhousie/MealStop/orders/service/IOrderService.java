package com.dalhousie.MealStop.orders.service;

import com.dalhousie.MealStop.orders.model.Orders;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IOrderService {
    public void addOrder(Orders newOrder);
    public abstract List<Orders> getAllOrders();

    public abstract List<Orders> getOrdersByCustomerID(long userId);

    public abstract Orders getOrderByOrderID(long userId);

    public abstract List<Orders> getOrdersByRestaurantID(long restaurantId);

    //abstract method to return most ordered meal by restaurant id and customer id
    public abstract List<Long> getMostOrderedMeal(long customerId, long restaurantId );

    //abstract method to return most ordered meal by restaurant
    public abstract List<Long> getMostOrderedMealOfRestaurant(long restaurantId);

    //abstract method to return most ordered meal by customer id
    public abstract List<Long> getMostOrderedMealOfCustomer(long customerId);
}
