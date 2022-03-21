package com.dalhousie.MealStop.orders.controller;

import com.dalhousie.MealStop.Meal.service.IMealService;
import com.dalhousie.MealStop.Restaurant.service.IRestaurantService;
import com.dalhousie.MealStop.cart.modal.CustomerCart;
import com.dalhousie.MealStop.cart.service.CustomerCartService;
import com.dalhousie.MealStop.customer.service.ICustomerService;
import com.dalhousie.MealStop.orders.Constants.Constants;
import com.dalhousie.MealStop.orders.Utils.Utils;
import com.dalhousie.MealStop.orders.model.Orders;
import com.dalhousie.MealStop.orders.service.IOrderService;
import com.dalhousie.MealStop.orders.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    private CustomerCartService customerCartService;

    @Autowired
    private ICustomerService customerService;


    @PostMapping("orders/add_order")
    String addNewOrders(Model model, @ModelAttribute CustomerCart cart, RedirectAttributes redirectAttrs)
    {
        CustomerCart customerCart = customerCartService.getCustomerCart();
        long customer = customerService.getCustomerDetailsFromSession().getId();
        System.out.println("card ** is :"+customer);

        orderService.CreateOrderFromCart(customerCart);
        redirectAttrs.addFlashAttribute("customer_id",customer);

        return "redirect:customer_orders_all";
    }

    @RequestMapping("orders/customer_orders_all")
    String customerOrdersNew(Model model, @ModelAttribute("customer_id") long id) {

        System.out.println("new id : "+id);
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

        System.out.println("list size : "+order_list.size());
        model.addAttribute("order_list", order_list);
        //return  "redirect:CustomerActiveOrders";
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

        List<OrdersPayload> order_list=new ArrayList<>();

        List<Orders> orders=orderService.getRestaurantOrdersWithStatus(id,status);

        for (Orders order:orders) {
            OrdersPayload payload=new OrdersPayload();
            payload.mealName = mealService.getMealByMealId(order.getMealId()).getMealName();
            payload.amount = order.getOrderAmount();
            payload.date = order.getOrderTime().toString();
            payload.status = Utils.getOrderStatusMapping(order.getOrderStatus());
            order_list.add(payload);
        }

        model.addAttribute("order_list", order_list);
        return  "orders/OrderDetails";
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

    @GetMapping("orders/report/id={id}&year={year}")
    String Report(Model model, @PathVariable("id") long id,@PathVariable("year") int year) {

        Map<String, Float> report_list=new HashMap<>();
        Map<Integer, Float> reportMap = orderService.getMonthlyReportofRestaurant(id,year);
        Iterator<Map.Entry<Integer, Float>> itr =  reportMap.entrySet().iterator();
        while(itr.hasNext()){

            Map.Entry<Integer, Float> entry = itr.next();
            report_list.put(Utils.getMonthMapping(entry.getKey()), entry.getValue());
        }
        model.addAttribute("report_list", report_list);
        return  "orders/MonthlyReport";
    }

    @GetMapping("orders/Enjoy")
    String FoodDelivered()
    {
        return "orders/Enjoy";
    }

}
