package com.dalhousie.MealStop.orders.service;
import com.dalhousie.MealStop.Restaurant.service.IRestaurantService;
import com.dalhousie.MealStop.cart.modal.CustomerCart;
import com.dalhousie.MealStop.cart.service.CustomerCartService;
import com.dalhousie.MealStop.customer.modal.Customer;
import com.dalhousie.MealStop.customer.service.ICustomerService;
import com.dalhousie.MealStop.orders.model.Orders;
import com.dalhousie.MealStop.orders.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dalhousie.MealStop.orders.Constants.Constants;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService implements IOrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private CustomerCartService customerCartService;


    //check if enough token is added
    //custoer get/set
    //customer servive.update

    @Override
    public void CreateOrderFromCart(CustomerCart cart){
       long customerId = customerService.getCustomerDetailsFromSession().getId();

       cart.getCartItems().forEach(item->{
           Long restaurantId=item.getRestaurant().getId();
           Long mealId=item.getId();
           Long price=item.getPrice();
           Orders order=new Orders(customerId,restaurantId,mealId,0,price,Constants.ACTIVE);
           addOrder(order);
           //decrement customer token after placing order
           customerService.decrementCustomerToken(price.intValue());
       });

       //clear customer cart after placing the order
        customerCartService.clearCustomerCart();
    }

    @Override
    public void addOrder(Orders newOrder){
        //this method adds new order that has been placed
        orderRepository.save(newOrder);
    }

    @Override
    public void updateOrderStatus(long orderId, int status){

        //this method updates order status that has been placed
        orderRepository.updateOrdersById(orderId,status);
    }

    @Override
    public List<Orders> getAllOrders(){
        //this method returns all the orders that has been placed so far
        return orderRepository.findAll();
    }

    @Override
    public List<Orders> getAllCanceledOrders(){

        //this method returns all the orders that are in the cancelled status
        return orderRepository.findByStatus(Constants.CANCELLED);
    }

    @Override
    public List<Orders> getCustomerOrdersWithStatus(long customerId, int status){

        return orderRepository.findByCustomerIdAndStatus(customerId,status);
    }

    @Override
    public List<Orders> getRestaurantOrdersWithStatus(long restaurantId, int status){

        return orderRepository.findByRestaurantIdAndStatus(restaurantId,status);
    }

    @Override
    public List<Orders> getOrdersByCustomerID(long userId){
        //this method returns all the orders placed by a customer using his customer id
        return orderRepository.findByCustomerId(userId);
    }

    @Override
    public Orders getOrderByOrderID(long orderId){
        //this method returns order detail using order id
        return orderRepository.findById(orderId);
    }

    @Override
    public List<Orders> getOrdersByRestaurantID(long restaurantId){
        //this method return restaurant details using restaurant id
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

    //this method returns monthly earnings of a restaurant by id provided
    public Map<Integer, Float> getMonthlyReportofRestaurant(long restaurantId, int year){
        List<Orders> orders= orderRepository.findAllByRestaurantIdandYear(restaurantId, year);
        Map<Integer, Float> monthlyReport=new HashMap<>();
        for (Orders order:orders) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(order.getOrderTime());
            int month = cal.get(Calendar.MONTH);
            if(!monthlyReport.containsKey(month)){
                monthlyReport.put(month,order.getOrderAmount());
            }else{
                float amt=monthlyReport.get(month);
                amt+=order.getOrderAmount();
                monthlyReport.put(month,amt);
            }
        }

        return monthlyReport;
    }
}
