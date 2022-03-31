package com.dalhousie.MealStop.orders.controller;

import com.dalhousie.MealStop.meal.service.IMealService;
import com.dalhousie.MealStop.restaurant.service.IRestaurantService;
import com.dalhousie.MealStop.cart.modal.CustomerCart;
import com.dalhousie.MealStop.cart.service.CustomerCartServiceImpl;
import com.dalhousie.MealStop.customer.service.ICustomerService;
import com.dalhousie.MealStop.orders.Constants.Constants;
import com.dalhousie.MealStop.orders.Utils.Utils;
import com.dalhousie.MealStop.orders.model.Orders;
import com.dalhousie.MealStop.orders.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.Date;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;


class OrdersPayload{
    public long orderId;
    public String mealName;
    public String date;
    public float amount;
    public String status;
    public String restaurantName;
    public String imageUrl;
}

class ReportPayload{
    public String monthName;
    public float amount;

}

@Controller
public class OrderController {
    @Autowired
    private IOrderService orderService;

    @Autowired
    private IRestaurantService restaurantService;

    @Autowired
    private IMealService mealService;

    @Autowired
    private CustomerCartServiceImpl customerCartServiceImpl;

    @Autowired
    private ICustomerService customerService;


    @PostMapping("orders/add_order")
    String addNewOrders(Model model, @ModelAttribute CustomerCart cart, RedirectAttributes redirectAttrs)
    {
        CustomerCart customerCart = customerCartServiceImpl.getCustomerCart();
        long customerId = customerService.getCustomerDetailsFromSession().getId();

        orderService.CreateOrderFromCart(customerCart);
        redirectAttrs.addFlashAttribute("customer_id",customerId);

        return "redirect:customer_orders_all";
    }

    @RequestMapping("orders/customer_orders_all")
    String customerOrdersNew(Model model, @ModelAttribute("customer_id") long id) {

        List<OrdersPayload> order_list=new ArrayList<>();
        List<Orders> orders=orderService.getCustomerOrdersWithStatus(id,Constants.ACTIVE);

        for (Orders order:orders) {
            OrdersPayload payload=new OrdersPayload();
            payload.orderId=order.getOrderId();
            payload.mealName = mealService.getMealByMealId(order.getMealId()).getMealName();
            payload.restaurantName=restaurantService.getRestaurantById(order.getRestaurantId()).getRestaurantName();
            payload.amount = order.getOrderAmount();
            payload.date = order.getOrderTime().toString();
            payload.status = Utils.getOrderStatusMapping(order.getOrderStatus());
            payload.imageUrl=Utils.getUrls().get(Utils.getRandomNumberUsingInts(0,Utils.getUrls().size()));
            order_list.add(payload);
        }

        model.addAttribute("order_list", order_list);
        return  "orders/CustomerActiveOrders";

    }

    @GetMapping("orders/cancelled_orders")
    String getAllCancelledOrders(Model model)
    {
        List<OrdersPayload> order_list=new ArrayList<>();
        List<Orders> listOrders = orderService. getAllCanceledOrders();

        for (Orders order:listOrders) {
            OrdersPayload payload=new OrdersPayload();
            payload.mealName = mealService.getMealByMealId(order.getMealId()).getMealName();
            payload.restaurantName=restaurantService.getRestaurantById(order.getRestaurantId()).getRestaurantName();
            payload.amount = order.getOrderAmount();
            payload.date = order.getOrderTime().toString();
            payload.status = Utils.getOrderStatusMapping(order.getOrderStatus());
            order_list.add(payload);
        }

        model.addAttribute("order_list", order_list);
        return "orders/OrderDetails";
    }

    @GetMapping("orders/restaurant_orders/id={id}&status={status}")
    String restaurantOrders(Model model, @PathVariable("id") long id,@PathVariable("status") int status) {

        List<Orders> orders=orderService.getRestaurantOrdersWithStatus(id,status);
        List<OrdersPayload> order_list=GetRestaurantOrdersList(orders);
        model.addAttribute("order_list", order_list);
        model.addAttribute("restaurant_id",id);
        return  "orders/OrderDetails";
    }

    @GetMapping("orders/restaurant_orders/{id}")
    String restaurantActiveOrders(Model model, @PathVariable("id") long id) {

        List<Orders> orders=orderService.getRestaurantOrdersWithStatus(id,Constants.ACTIVE);
        List<OrdersPayload> order_list=GetRestaurantOrdersList(orders);
        model.addAttribute("order_list", order_list);
        return  "orders/OrderDetails";

    }

    List<OrdersPayload> GetRestaurantOrdersList(List<Orders> orders){
        List<OrdersPayload> order_list=new ArrayList<>();
        for (Orders order:orders) {
            OrdersPayload payload=new OrdersPayload();
            payload.mealName = mealService.getMealByMealId(order.getMealId()).getMealName();
            payload.amount = order.getOrderAmount();
            payload.date = order.getOrderTime().toString();
            payload.status = Utils.getOrderStatusMapping(order.getOrderStatus());
            order_list.add(payload);
        }
        return order_list;
    }

    @GetMapping("orders/customer_orders")
    String customerAllOrders(Model model) {

        List<OrdersPayload> order_list=new ArrayList<>();
        long id = customerService.getCustomerDetailsFromSession().getId();
        List<Orders> orders=orderService.getOrdersByCustomerID(id);

        for (Orders order:orders) {
            OrdersPayload payload=new OrdersPayload();
            payload.orderId=order.getOrderId();
            payload.mealName = mealService.getMealByMealId(order.getMealId()).getMealName();
            payload.restaurantName=restaurantService.getRestaurantById(order.getRestaurantId()).getRestaurantName();
            payload.amount = order.getOrderAmount();
            payload.date = order.getOrderTime().toString();
            payload.status = Utils.getOrderStatusMapping(order.getOrderStatus());
            payload.imageUrl=Utils.getUrls().get(Utils.getRandomNumberUsingInts(0,Utils.getUrls().size()));
            order_list.add(payload);
        }

        model.addAttribute("order_list", order_list);

        return  "orders/CustomerOrderDetails";

    }

    @GetMapping("orders/customer_orders/id={id}&status={status}")
    String customerOrders(Model model, @PathVariable("id") long id,@PathVariable("status") int status) {

        List<OrdersPayload> order_list=new ArrayList<>();
        List<Orders> orders=orderService.getCustomerOrdersWithStatus(id,status);

        for (Orders order:orders) {
            OrdersPayload payload=new OrdersPayload();
            payload.orderId=order.getOrderId();
            payload.mealName = mealService.getMealByMealId(order.getMealId()).getMealName();
            payload.restaurantName=restaurantService.getRestaurantById(order.getRestaurantId()).getRestaurantName();
            payload.amount = order.getOrderAmount();
            payload.date = order.getOrderTime().toString();
            payload.status = Utils.getOrderStatusMapping(order.getOrderStatus());
            payload.imageUrl=Utils.getUrls().get(Utils.getRandomNumberUsingInts(0,Utils.getUrls().size()));
            order_list.add(payload);
        }

        model.addAttribute("order_list", order_list);

        Boolean isOrderActive=status!= Constants.CANCELLED || status!= Constants.DELIVERED;
        return  isOrderActive?"orders/CustomerActiveOrders":"orders/CustomerOrderDetails";

    }
    
    @RequestMapping(value = "/updateOrder/{id}")
    public String updateOrder(@PathVariable("id") long orderId, @ModelAttribute OrdersPayload payload) {

        orderService.updateOrderStatus(orderId,Constants.DELIVERED);
        return "orders/Enjoy";
    }

    @RequestMapping(value = "/cancelOrder/{id}")
    public String cancelOrder(@PathVariable("id") long orderId, @ModelAttribute OrdersPayload payload) {

        orderService.updateOrderStatus(orderId,Constants.CANCELLED);
        return "orders/CustomerActiveOrders";
    }

    @RequestMapping(value = "/restaurantUpdateOrder/{id}")
    public String restaurantUpdateOrder(@PathVariable("id") long orderId, @ModelAttribute OrdersPayload payload) {

        orderService.updateOrderStatus(orderId,Constants.PROCESSED);
        //TODO need the redirect
        return "orders/Enjoy";
    }

    @RequestMapping("orders/report/{id}")
    String Report(Model model, @PathVariable("id") long id) {


        Map<String, Float> report_list=new HashMap<>();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        Map<Integer, Float> reportMap = orderService.getMonthlyReportofRestaurant(id,2022);
        Iterator<Map.Entry<Integer, Float>> itr =  reportMap.entrySet().iterator();
        while(itr.hasNext()){
            
            Map.Entry<Integer, Float> entry = itr.next();
            report_list.put(Utils.getMonthMapping(entry.getKey()), entry.getValue());
        }
        System.out.println(report_list.size());
        model.addAttribute("report_list", report_list);
        return  "orders/MonthlyReport";
    }

    @GetMapping("orders/Enjoy")
    String FoodDelivered()
    {
        return "orders/Enjoy";
    }

}
