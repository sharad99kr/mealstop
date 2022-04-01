package com.dalhousie.MealStop.orders.service;

import com.dalhousie.MealStop.cart.modal.CustomerCart;
import com.dalhousie.MealStop.orders.controller.OrdersPayload;
import com.dalhousie.MealStop.orders.model.Orders;

import java.io.Writer;
import java.util.List;
import java.util.Map;

public interface IOrderService {

    public void CreateOrderFromCart(CustomerCart cart);

    //method signature that adds new order
    public void addOrder(Orders newOrder);

    //method signature that returns all the orders
    public List<Orders> getAllOrders();

    //method signature returns all the orders that are in the cancelled status
    public List<Orders> getAllCanceledOrders();

    //method signature to get all orders by user id
    public List<Orders> getOrdersByCustomerID(long userId);

    //method signature to get all orders by order id
    public Orders getOrderByOrderID(long orderId);

    //method signature to update order status
    public void updateOrderStatus(long orderId, int status);

    public List<Orders> getOrdersByRestaurantID(long restaurantId);

    //method signature to return most ordered meal by restaurant id and customer id
    public List<Long> getMostOrderedMeal(long customerId, long restaurantId );

    //method signature to return most ordered meal by restaurant
    public List<Long> getMostOrderedMealOfRestaurant(long restaurantId);

    //method signature to return most ordered meal by customer id
    public List<Long> getMostOrderedMealOfCustomer(long customerId);

    //method signature to return monthly report of earnings for a Restaurant
    public Map<Integer, Float> getMonthlyReportofRestaurant(long restaurantId, int year);

    //method signature to update order claimed by NGO
    public void claimedByNGO(long ngoId, long orderId);

    public List<Orders> getCustomerOrdersWithStatus(long customerId, int status);

    public List<Orders> getOrdersForNGO(long ngoId);

    public List<Orders> getRestaurantOrdersWithStatus(long restaurantId, int status);

    public void writeEarningsToCsv(Writer writer, long id);

}
