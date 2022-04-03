package com.dalhousie.MealStop.orders.service;
import com.dalhousie.MealStop.ngoorder.model.NGOOrder;
import com.dalhousie.MealStop.ngoorder.service.INGOOrderService;
import com.dalhousie.MealStop.Reward.service.IRewardService;
import com.dalhousie.MealStop.cart.model.CustomerCart;
import com.dalhousie.MealStop.cart.service.CustomerCartServiceImpl;
import com.dalhousie.MealStop.customer.service.ICustomerService;
import com.dalhousie.MealStop.meal.service.IMealService;
import com.dalhousie.MealStop.orders.Utils.Utils;
import com.dalhousie.MealStop.orders.model.Orders;
import com.dalhousie.MealStop.orders.repository.OrderRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dalhousie.MealStop.common.OrderConstants;

import java.io.IOException;
import java.io.Writer;
import java.util.*;


@Service
public class OrderService implements IOrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private CustomerCartServiceImpl customerCartServiceImpl;

    @Autowired
    private IMealService mealService;

    @Autowired
    private IRewardService rewardService;

    @Autowired
    private INGOOrderService ngoOrderService;


    @Override
    public void CreateOrderFromCart(CustomerCart cart){
       long customerId = customerService.getCustomerDetailsFromSession().getId();

       cart.getCartItems().forEach(item->{
           Long restaurantId=item.getRestaurant().getId();
           Long mealId=item.getId();
           Long price=item.getPrice();
           Orders order=new Orders(customerId,restaurantId,mealId,0,price, OrderConstants.ACTIVE);

           int tokenCount = customerService.getCustomerTokenCount();
           if(tokenCount> price){
               //decrement customer token after placing order
               addOrder(order);
               customerService.decrementCustomerToken(price.intValue());
               rewardService.addRewardPoints(customerId);
           }else{
               System.out.println("Not enough token");
           }

       });

       //clear customer cart after placing the order
        customerCartServiceImpl.clearCustomerCart();
    }



    @Override
    public void addOrder(Orders newOrder){
        //this method adds new order that has been placed
        orderRepository.save(newOrder);
    }

    @Override
    public List<Orders> getOrdersForNGO(long ngoId){

        List<NGOOrder> ngoOrders=ngoOrderService.getNGOOrderWithId(ngoId);
        List<Orders> orders=new ArrayList<>();
        for (NGOOrder ngorder:ngoOrders) {
            long id = ngorder.getOrderId();
            Orders order1=getOrderByOrderID(id);
            orders.add(order1);
        }

        return orders;

    }

    @Override
    public void updateOrderStatus(long orderId, int status){

        //this method updates order status that has been placed
        orderRepository.updateOrdersById(orderId,status);
    }

    @Override
    public void claimedByNGO(long ngoId, long orderId){
        //this method updates and links an order with NGO
        updateOrderStatus( orderId, OrderConstants.CLAIMED);
        NGOOrder ngoOrder=new NGOOrder(orderId,ngoId, OrderConstants.CLAIMED);
        ngoOrderService.addNGOOrder(ngoOrder);
    }


    @Override
    public List<Orders> getAllCanceledOrders(){

        //this method returns all the orders that are in the cancelled status
        return orderRepository.findByStatus(OrderConstants.CANCELLED);
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

    public void writeEarningsToCsv(Writer writer, long id) {

        //https://springhow.com/spring-boot-export-to-csv/
        //used above link as reference to export csv file
        Map<String, Float> report_list=new HashMap<>();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        Map<Integer, Float> reportMap = getMonthlyReportofRestaurant(id,year);
        Iterator<Map.Entry<Integer, Float>> itr =  reportMap.entrySet().iterator();
        while(itr.hasNext()){

            Map.Entry<Integer, Float> entry = itr.next();
            report_list.put(Utils.getMonthMapping(entry.getKey()), entry.getValue());
        }

        try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
            csvPrinter.printRecord(String.format(OrderConstants.MONTHLY_REPORT,year));
            csvPrinter.printRecord(OrderConstants.MONTH_HEADER, OrderConstants.EARNINGS_HEADER);
            for (String reportKeys : report_list.keySet()) {
                csvPrinter.printRecord(reportKeys, report_list.get(reportKeys));
            }
        } catch (IOException e) {
            System.out.println(OrderConstants.FILE_WRITE_ERROR);
        }
    }
    @Override    public List<Orders> getAllOrders(){
        return orderRepository.findAll();    }
}
