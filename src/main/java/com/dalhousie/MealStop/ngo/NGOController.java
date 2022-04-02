package com.dalhousie.MealStop.ngo;

import com.dalhousie.MealStop.NGOOrder.model.NGOOrder;
import com.dalhousie.MealStop.NGOOrder.service.INGOOrderService;
import com.dalhousie.MealStop.meal.service.IMealService;
import com.dalhousie.MealStop.ngo.model.NGO;
import com.dalhousie.MealStop.ngo.service.INGOService;
import com.dalhousie.MealStop.orders.Utils.Utils;
import com.dalhousie.MealStop.orders.controller.OrdersPayload;
import com.dalhousie.MealStop.orders.model.Orders;
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

//    @GetMapping("/ngo/homepage")
//    public String getLandingPage(Model model) {
//        List<Orders> orders = orderService.getAllCanceledOrders();
//        model.addAttribute("orders", orders);
//        return "ngo/landing-page";
//    }


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
            System.out.println(payload.mealName);
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
        List<Orders> orders=orderService.getAllOrders();
        List<NGOOrder> ngoOrders=ngoOrderService.getNGOOrderWithId(ngoUser.getId());
        System.out.println(ngoOrders.size());
        System.out.println(orders.size());
        List<Orders> filteredOrders= new ArrayList<>();

        for(NGOOrder ngoOrder : ngoOrders)
        {
            for(Orders order : orders)
            {
                System.out.println(ngoOrder.getOrderId());
                System.out.println(order.getOrderId());
                if(ngoOrder.getOrderId() == order.getOrderId())
                    filteredOrders.add(order);
            }
        }
        System.out.println(filteredOrders.size());
        for(Orders order : filteredOrders)
        {
            System.out.println(order);
        }
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