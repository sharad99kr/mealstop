package com.dalhousie.MealStop.orders.controller;

import com.dalhousie.MealStop.orders.model.Order;
import com.dalhousie.MealStop.orders.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class OrderController {
//    @Autowired
//    private OrderService orderService;
//
//    @GetMapping("/get_all_orders")
//    public String getAllOrders(Model model)
//    {
//        List<Order> listOrders = orderService.getAllOrders();
//        model.addAttribute("orders_list", listOrders);
//        return "user/get_user";
//    }

    @GetMapping("/")
    String index(Principal principal) {
        return principal != null ? "orders/OrderDetails" : "orders/OrderDetails";
    }
}
