package com.dalhousie.MealStop.orders.controller;

import com.dalhousie.MealStop.orders.Constants.Constants;
import com.dalhousie.MealStop.orders.Utils.Utils;
import com.dalhousie.MealStop.orders.model.Orders;
import com.dalhousie.MealStop.orders.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

class OrdersPayload{
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
    private OrderService orderService;

    @GetMapping("orders/cancelled_orders")
    String getAllCancelledOrders(Model model)
    {
        List<OrdersPayload> order_list=new ArrayList<>();
        List<Orders> listOrders = orderService. getAllCanceledOrders();

        for (Orders order:listOrders) {
            OrdersPayload payload=new OrdersPayload();
            payload.mealName = "biryani";
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
            payload.mealName = "biryani";
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
            payload.mealName = "biryani";
            payload.restaurantName="Stoner";
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


    @PostMapping("orders/customer_orders/id={id}&status={status}")
    String customerOrdersSubmot(Model model, @PathVariable("id") long id,@PathVariable("status") int status)
    {
        return "redirect:/orders/Enjoy";
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
    String FoodDelivered(){
        return "orders/Enjoy";
    }

}
