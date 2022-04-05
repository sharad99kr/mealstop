package com.dalhousie.MealStop.orders.service;

import com.dalhousie.MealStop.cart.model.CustomerCart;
import com.dalhousie.MealStop.orders.model.Orders;

import java.io.Writer;
import java.util.List;
import java.util.Map;

public interface IOrderService {

     void CreateOrderFromCart(CustomerCart cart);

     void addOrder(Orders newOrder);

     List<Orders> getAllCanceledOrders();

     List<Orders> getOrdersByCustomerID(long userId);

     Orders getOrderByOrderID(long orderId);

     boolean updateOrderStatus(long orderId, int status);

     List<Orders> getOrdersByRestaurantID(long restaurantId);

     Map<Integer, Float> getMonthlyReportofRestaurant(long restaurantId, int year);

     void claimedByNGO(long ngoId, long orderId);

     List<Orders> getCustomerOrdersWithStatus(long customerId, int status);

     List<Orders> getOrdersForNGO(long ngoId);

     List<Orders> getRestaurantOrdersWithStatus(long restaurantId, int status);

     void writeEarningsToCsv(Writer writer, long id);

     List<Orders> getAllOrders();
}
