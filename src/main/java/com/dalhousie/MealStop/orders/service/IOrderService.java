package com.dalhousie.MealStop.orders.service;

import com.dalhousie.MealStop.cart.model.CustomerCart;
import com.dalhousie.MealStop.orders.model.Orders;

import java.io.Writer;
import java.util.List;
import java.util.Map;

public interface IOrderService {

     void CreateOrderFromCart(CustomerCart cart);

    //method signature that adds new order
     void addOrder(Orders newOrder);

    //method signature returns all the orders that are in the cancelled status
     List<Orders> getAllCanceledOrders();

    //method signature to get all orders by user id
     List<Orders> getOrdersByCustomerID(long userId);

    //method signature to get all orders by order id
     Orders getOrderByOrderID(long orderId);

    //method signature to update order status
     boolean updateOrderStatus(long orderId, int status);

     List<Orders> getOrdersByRestaurantID(long restaurantId);


    //method signature to return monthly report of earnings for a Restaurant
     Map<Integer, Float> getMonthlyReportofRestaurant(long restaurantId, int year);

    //method signature to update order claimed by NGO
     void claimedByNGO(long ngoId, long orderId);

     List<Orders> getCustomerOrdersWithStatus(long customerId, int status);

     List<Orders> getOrdersForNGO(long ngoId);

     List<Orders> getRestaurantOrdersWithStatus(long restaurantId, int status);

     void writeEarningsToCsv(Writer writer, long id);

     List<Orders> getAllOrders();



}
