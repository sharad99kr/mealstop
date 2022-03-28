package com.dalhousie.MealStop.orders.service;

import com.dalhousie.MealStop.cart.modal.CustomerCart;
import com.dalhousie.MealStop.orders.model.Orders;

import java.util.List;
import java.util.Map;

public interface IOrderService {

    public void CreateOrderFromCart(CustomerCart cart);

    //method signature that adds new order
    public abstract void addOrder(Orders newOrder);

    //abstract method that returns all the orders
    public abstract List<Orders> getAllOrders();

    //abstract method returns all the orders that are in the cancelled status
    public abstract List<Orders> getAllCanceledOrders();

    //abstract method to get all orders by user id
    public abstract List<Orders> getOrdersByCustomerID(long userId);

    //abstract method to get all orders by order id
    public abstract Orders getOrderByOrderID(long orderId);

    //abstract method to update order status
    public abstract void updateOrderStatus(long orderId, int status);

    public abstract List<Orders> getOrdersByRestaurantID(long restaurantId);

    //abstract method to return most ordered meal by restaurant id and customer id
    public abstract List<Long> getMostOrderedMeal(long customerId, long restaurantId );

    //abstract method to return most ordered meal by restaurant
    public abstract List<Long> getMostOrderedMealOfRestaurant(long restaurantId);

    //abstract method to return most ordered meal by customer id
    public abstract List<Long> getMostOrderedMealOfCustomer(long customerId);

    //abstract method to return monthly report of earnings for a Restaurant
    public abstract Map<Integer, Float> getMonthlyReportofRestaurant(long restaurantId, int year);


    public abstract List<Orders> getCustomerOrdersWithStatus(long customerId, int status);



    public abstract List<Orders> getRestaurantOrdersWithStatus(long restaurantId, int status);
}
