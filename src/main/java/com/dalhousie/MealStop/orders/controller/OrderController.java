package com.dalhousie.MealStop.orders.controller;

import com.dalhousie.MealStop.cart.service.ICustomerCartService;
import com.dalhousie.MealStop.meal.service.IMealService;
import com.dalhousie.MealStop.ngo.service.INGOService;
import com.dalhousie.MealStop.orders.model.OrdersPayload;
import com.dalhousie.MealStop.restaurant.service.IRestaurantService;
import com.dalhousie.MealStop.cart.model.CustomerCart;
import com.dalhousie.MealStop.customer.service.ICustomerService;
import com.dalhousie.MealStop.common.OrderConstants;
import com.dalhousie.MealStop.orders.Utils.Utils;
import com.dalhousie.MealStop.orders.model.Orders;
import com.dalhousie.MealStop.orders.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.io.IOException;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.util.*;


@Controller
public class OrderController {
    @Autowired
    private IOrderService orderService;

    @Autowired
    private IRestaurantService restaurantService;

    @Autowired
    private IMealService mealService;

    @Autowired
    private INGOService ngoService;

    @Autowired
    private ICustomerCartService customerCartService;

    @Autowired
    private ICustomerService customerService;


    @PostMapping(OrderConstants.ADD_ORDER)
    String addNewOrders(Model model, RedirectAttributes redirectAttrs)
    {
        CustomerCart customerCart = customerCartService.getCustomerCart();
        long customerId = customerService.getCustomerDetailsFromSession().getId();
        orderService.CreateOrderFromCart(customerCart);
        redirectAttrs.addFlashAttribute("customer_id",customerId);

        return "redirect:customer_orders_all";
    }

    @RequestMapping(OrderConstants.GET_ALL_ORDER)
    String allCustomerOrders(Model model) {

        //this method gets all the active orders for a customer and sends to frontend to be displayed on customer order page
        long customerId = customerService.getCustomerDetailsFromSession().getId();
        List<OrdersPayload> order_list= geOrdersPayloadForCustomers( customerId, OrderConstants.ACTIVE );
        model.addAttribute("order_list", order_list);
        return  "orders/CustomerActiveOrders";

    }

    @RequestMapping(OrderConstants.GET_PROCESSED_ORDER)
    String customerProcessedOrders(Model model) {

        //this method gets all the processed orders for a customer and sends to frontend to be displayed on customer order page
        long customerId = customerService.getCustomerDetailsFromSession().getId();
        List<OrdersPayload> order_list= geOrdersPayloadForCustomers( customerId, OrderConstants.PROCESSED );
        model.addAttribute("order_list", order_list);
        return  "orders/CustomerProcessedOrders";

    }

    @GetMapping(OrderConstants.GET_CANCELLED_ORDER)
    String getAllCancelledOrders(Model model)
    {
        //this method gets all the cancelled orders for a customer and sends to frontend to be displayed on customer order page
        List<Orders> listOrders = orderService.getAllCanceledOrders();
        List<OrdersPayload> order_list=getCancelledOrdersPayload(listOrders);
        model.addAttribute("order_list", order_list);
        return "orders/OrderDetails";
    }



    @GetMapping(OrderConstants.GET_ORDER_BY_RESTAURANT_ID)
    String restaurantActiveOrders(Model model, @PathVariable("id") long id) {

        //this method gets all the active orders for a restaurant and sends to frontend to be displayed on restaurant orders page
        List<Orders> orders=orderService.getRestaurantOrdersWithStatus(id, OrderConstants.ACTIVE);
        List<OrdersPayload> order_list= GetOrdersPayloadList(orders);
        model.addAttribute("order_list", order_list);
        model.addAttribute("restaurant_id",id);
        return  "orders/OrderDetails";

    }


    @GetMapping("orders/customer_orders")
    String customerAllOrders(Model model) {
        //this method gets all the cancelled and delivered orders for a customer and sends to frontend to be displayed on customer order page
        long id = customerService.getCustomerDetailsFromSession().getId();
        List<Orders> cancelledOrders=orderService.getCustomerOrdersWithStatus(id, OrderConstants.CANCELLED);
        List<Orders> deliveredOrders=orderService.getCustomerOrdersWithStatus(id, OrderConstants.DELIVERED);
        List<Orders> orders=new ArrayList<>();
        orders.addAll(cancelledOrders);
        orders.addAll(deliveredOrders);
        List<OrdersPayload> order_list= GetOrdersPayloadList(orders);

        model.addAttribute("order_list", order_list);

        return  "orders/CustomerOrderDetails";

    }


    
    @RequestMapping(value = "/updateOrder/{id}")
    public String updateOrder(@PathVariable("id") long orderId, @ModelAttribute OrdersPayload payload) {

        //this method is appreciation page for customers who acknowledge food has been delivered
        orderService.updateOrderStatus(orderId, OrderConstants.DELIVERED);
        return "orders/Enjoy";
    }

    @RequestMapping(value = "/cancelOrder/{id}")
    public String cancelOrder(Model model, @PathVariable("id") long orderId, @ModelAttribute OrdersPayload payload) {

        //this method receives request from customer to cancel an order and update the same on backend. It navigates the back to the same page after that
        orderService.updateOrderStatus(orderId, OrderConstants.CANCELLED);
        Orders order=orderService.getOrderByOrderID(orderId);
        List<OrdersPayload> orders= geOrdersPayloadForCustomers( order.getCustomerId(), OrderConstants.ACTIVE);

        String mealName = mealService.getMealByMealId(order.getMealId()).getMealName();
        ngoService.sendCancelledOrderNotification(mealName);
        model.addAttribute("order_list", orders);
        return "orders/CustomerActiveOrders";
    }



    @RequestMapping(value = "/restaurantUpdateOrder/{id}")
    public String restaurantUpdateOrder(Model model,@PathVariable("id") long orderId, @ModelAttribute OrdersPayload payload) {

        //this method receives request from restaurant after processing an order and update the same on backend. It navigates the back to the same page after that
        orderService.updateOrderStatus(orderId, OrderConstants.PROCESSED);
        Orders order= orderService.getOrderByOrderID(orderId);
        return OrderConstants.RESTAURANT_REDIRECTION_URL+order.getRestaurantId();

    }

    @RequestMapping("orders/report/{id}")
    String Report(Model model, @PathVariable("id") long id) {

        //this method gets the monthly report and sends it to the front end for restaurant to view and print the report
        Map<String, Float> report_list=new HashMap<>();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        Map<Integer, Float> reportMap = orderService.getMonthlyReportofRestaurant(id,year);
        Iterator<Map.Entry<Integer, Float>> itr =  reportMap.entrySet().iterator();
        while(itr.hasNext()){
            
            Map.Entry<Integer, Float> entry = itr.next();
            report_list.put(Utils.getMonthMapping(entry.getKey()), entry.getValue());
        }
        model.addAttribute("report_list", report_list);
        model.addAttribute("restaurant_id", id);

        return  "orders/MonthlyReport";
    }

    @RequestMapping("orders/generateReport/{id}")
    String GenerateCsv(Model model, @PathVariable("id") long id, HttpServletResponse servletResponse) throws IOException {

        //this method receieves the request to write the report to the fille when clicked on the export button
        servletResponse.setContentType("text/csv");
        servletResponse.addHeader("Content-Disposition","attachment; filename=\"Earnings.csv\"");
        orderService.writeEarningsToCsv(servletResponse.getWriter(), id);
        return  "orders/MonthlyReport";
    }

    public List<OrdersPayload> getCancelledOrdersPayload( List<Orders> listOrders){

        List<OrdersPayload> order_list=new ArrayList<>();
        if(listOrders!=null){
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
        }

        return order_list;
    }

    public List<OrdersPayload> geOrdersPayloadForCustomers(long id , int status){

        List<Orders> orders=orderService.getCustomerOrdersWithStatus(id,status);
        return GetOrdersPayloadList( orders);
    }

    public List<OrdersPayload> GetOrdersPayloadList(List<Orders> orders){

        //this method generates payload for orders for a restaurant and sends to frontend to be displayed on restaurant orders page
        List<OrdersPayload> order_list=new ArrayList<>();
        for (int i=0;i<orders.size(); i++) {
            OrdersPayload payload=new OrdersPayload();
            payload.orderId=orders.get(i).getOrderId();
            payload.mealName = mealService.getMealByMealId(orders.get(i).getMealId()).getMealName();
            payload.restaurantName=restaurantService.getRestaurantById(orders.get(i).getRestaurantId()).getRestaurantName();
            payload.amount = orders.get(i).getOrderAmount();
            payload.date = orders.get(i).getOrderTime().toString();
            payload.status = Utils.getOrderStatusMapping(orders.get(i).getOrderStatus());
            payload.imageUrl=Utils.getUrls().get(Utils.getRandomNumberUsingInts(0,Utils.getUrls().size()));
            order_list.add(payload);
        }
        return order_list;
    }

}
