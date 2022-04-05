package com.dalhousie.MealStop.ngo.controller;

import com.dalhousie.MealStop.ngoorder.service.INGOOrderService;
import com.dalhousie.MealStop.meal.service.IMealService;
import com.dalhousie.MealStop.ngo.model.NGO;
import com.dalhousie.MealStop.ngo.service.INGOService;
import com.dalhousie.MealStop.orders.utils.Utils;
import com.dalhousie.MealStop.orders.model.Orders;
import com.dalhousie.MealStop.orders.model.OrdersPayload;
import com.dalhousie.MealStop.orders.service.IOrderService;
import com.dalhousie.MealStop.restaurant.service.IRestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class NGOController {
    @Autowired
    private IOrderService orderService;

    @Autowired
    private IRestaurantService restaurantService;

    @Autowired
    private IMealService mealService;

    @Autowired
    private INGOOrderService ngoOrderService;

    @Autowired
    INGOService ngoService;

    @GetMapping("/ngo/homepage")
    String getLandingPage(Model model)
    {
        List<OrdersPayload> order_list=new ArrayList<>();
        List<Orders> listOrders = orderService. getAllCanceledOrders();
        order_list=getCancelledOrdersPayload(listOrders);
        model.addAttribute("order_list", order_list);
        return "orders/NGOActiveOrders";
    }

    List<OrdersPayload> getCancelledOrdersPayload( List<Orders> listOrders){
        List<OrdersPayload> order_list=new ArrayList<>();
        for (Orders order:listOrders) {
            OrdersPayload payload=new OrdersPayload();
            payload.orderId=order.getOrderId();
            payload.mealName = mealService.getMealByMealId(order.getMealId()).getMealName();
            payload.restaurantName=restaurantService.getRestaurantById(order.getRestaurantId()).getRestaurantName();
            payload.amount = order.getOrderAmount();
            payload.date = order.getOrderTime().toString();
            payload.status = Utils.getOrderStatusMapping(order.getOrderStatus());
            order_list.add(payload);
        }
        return order_list;
    }

    @GetMapping("/ngo/orders/ngo_accepted_order/{id}")
    String ngoAcceptedOrders(Model model, @PathVariable("id") long orderId) {
        NGO ngoUser = ngoService.getNGODetailsFromSession();
        orderService.claimedByNGO(ngoUser.getId(),orderId);
        List<Orders> filteredOrders = ngoService.getNGOOrderHistory();
        List<OrdersPayload> order_list=getCancelledOrdersPayload(filteredOrders);
        model.addAttribute("order_list", order_list);
        return  "orders/NGOOrderDetails";

    }

    @GetMapping("/ngo/orders/ngo_old_order")
    public String getNgoPastOrders(Model model) {
        List<Orders> filteredOrders = ngoService.getNGOOrderHistory();
        List<OrdersPayload> order_list=getCancelledOrdersPayload(filteredOrders);
        model.addAttribute("order_list", order_list);
        return  "orders/NGOOrderDetails";
    }


    @GetMapping("/ngo/profile")
    public String getCustomerProfilePage(Model model)
    {
        NGO ngoUser = ngoService.getNGODetailsFromSession();
        model.addAttribute("ngoUser", ngoUser);
        return "ngo/profile";
    }



}