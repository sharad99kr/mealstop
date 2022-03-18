package com.dalhousie.MealStop.orders.controller;

import com.dalhousie.MealStop.orders.Utils.Utils;
import com.dalhousie.MealStop.orders.model.Orders;
import com.dalhousie.MealStop.orders.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

class OrdersPayload{
    public String mealName;
    public String date;
    public float amount;
    public String status;

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
        return "orders/RestaurantOrderDetails";
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
        return  "orders/RestaurantOrderDetails";
    }

    @GetMapping("orders/customer_orders/id={id}&status={status}")
    String customerOrders(Model model, @PathVariable("id") long id,@PathVariable("status") int status) {

        List<OrdersPayload> order_list=new ArrayList<>();
        List<Orders> orders=orderService.getCustomerOrdersWithStatus(id,status);

        for (Orders order:orders) {
            OrdersPayload payload=new OrdersPayload();
            payload.mealName = "biryani";
            payload.amount = order.getOrderAmount();
            payload.date = order.getOrderTime().toString();
            payload.status = Utils.getOrderStatusMapping(order.getOrderStatus());
            order_list.add(payload);
        }

        model.addAttribute("order_list", order_list);
        return  "orders/RestaurantOrderDetails";
    }

    @GetMapping("/")
    String Report(Model model) {

        List<ReportPayload> report_list=new ArrayList<>();
        model.addAttribute("report_list", report_list);
        return  "orders/MonthlyReport";
    }
}
