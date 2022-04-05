package com.dalhousie.MealStop.orders.service;
import com.dalhousie.MealStop.ngoorder.model.NGOOrder;
import com.dalhousie.MealStop.ngoorder.service.INGOOrderService;
import com.dalhousie.MealStop.reward.service.IRewardService;
import com.dalhousie.MealStop.cart.model.CustomerCart;
import com.dalhousie.MealStop.cart.service.CustomerCartServiceImpl;
import com.dalhousie.MealStop.customer.service.ICustomerService;
import com.dalhousie.MealStop.orders.utils.Utils;
import com.dalhousie.MealStop.orders.model.Orders;
import com.dalhousie.MealStop.orders.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dalhousie.MealStop.common.OrderConstants;

import java.io.IOException;
import java.io.Writer;
import java.util.*;

@Slf4j
@Service
public class OrderService implements IOrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private CustomerCartServiceImpl customerCartServiceImpl;

    @Autowired
    private IRewardService rewardService;

    @Autowired
    private INGOOrderService ngoOrderService;


    @Override
    public void CreateOrderFromCart(CustomerCart cart){
       long customerId = customerService.getCustomerDetailsFromSession().getId();

       //generate order from the cart
       cart.getCartItems().forEach(item->{
           Long restaurantId=item.getRestaurant().getId();
           Long mealId=item.getId();
           Long price=item.getPrice();
           Orders order=new Orders();
           order.setOrderStatus(OrderConstants.ACTIVE);
           order.setOrderTime();
           order.setRestaurantId(restaurantId);
           order.setOrderAmount(Math.toIntExact(price));
           order.setCustomerId(customerId);
           order.setMealId(mealId);

           int tokenCount = customerService.getCustomerTokenCount();
           if(tokenCount >= price){
               //decrement customer token after placing order
               addOrder(order);
               customerService.decrementCustomerToken(price.intValue());
               rewardService.addRewardPoints(customerId);
           }else{
               log.error("Not enough token");
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
        for(int i=0; i<ngoOrders.size();i++){
            long id = ngoOrders.get(i).getOrderId();
            Orders order1=getOrderByOrderID(id);
            orders.add(order1);
        }
        return orders;

    }

    @Override
    public boolean updateOrderStatus(long orderId, int status){

        //this method updates order status that has been placed
        orderRepository.updateOrdersById(orderId,status);
        return true;
    }

    @Override
    public void claimedByNGO(long ngoId, long orderId){
        //this method updates and links an order with NGO
        updateOrderStatus( orderId, OrderConstants.CLAIMED);
        NGOOrder ngoOrder=new NGOOrder(orderId,ngoId, OrderConstants.CLAIMED);
        List<NGOOrder> ngoOrders
                =ngoOrderService.getNGOOrderWithId(ngoId);

        boolean isPresent = false;
        for(NGOOrder order : ngoOrders)
        {
            if(order.getOrderId() == orderId)
                isPresent = true;
        }

        if(!isPresent)
            ngoOrderService.addNGOOrder(ngoOrder);
    }


    @Override
    public List<Orders> getAllCanceledOrders(){

        //this method returns all the orders that are in the cancelled status
        return orderRepository.findByStatus(OrderConstants.CANCELLED);
    }

    @Override
    public List<Orders> getCustomerOrdersWithStatus(long customerId, int status){

        //get all orders for a customer with matching ID and ordered food status
        return orderRepository.findByCustomerIdAndStatus(customerId,status);
    }

    @Override
    public List<Orders> getRestaurantOrdersWithStatus(long restaurantId, int status){

        //get all orders for restaurants with matching ID and ordered food status
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

        //get all orders from restaurant based on id and year
        List<Orders> orders= orderRepository.findAllByRestaurantIdandYear(restaurantId, year);
        Map<Integer, Float> monthlyReport=new HashMap<>();
        //loop through each order and calculate the sum per month basis
        for (Orders order:orders) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(order.getOrderTime());
            int month = cal.get(Calendar.MONTH);
            if(!monthlyReport.containsKey(month)){
                //if entry for particular month is not present , add into the map with corresponding amount
                monthlyReport.put(month,order.getOrderAmount());
            }else{
                //if entry is found , update the earnings for that particular month
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
        //getting current year
        int year = Calendar.getInstance().get(Calendar.YEAR);
        //getting sales on monthly basis
        Map<Integer, Float> reportMap = getMonthlyReportofRestaurant(id,year);
        Iterator<Map.Entry<Integer, Float>> itr =  reportMap.entrySet().iterator();
        while(itr.hasNext()){
            //getting month's name based on month index
            Map.Entry<Integer, Float> entry = itr.next();
            report_list.put(Utils.getMonthMapping(entry.getKey()), entry.getValue());
        }

        try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
            //formatting output string and writing to a file
            csvPrinter.printRecord(String.format(OrderConstants.MONTHLY_REPORT,year));
            csvPrinter.printRecord(OrderConstants.MONTH_HEADER, OrderConstants.EARNINGS_HEADER);
            for (String reportKeys : report_list.keySet()) {
                csvPrinter.printRecord(reportKeys, report_list.get(reportKeys));
            }
        } catch (IOException e) {
            log.error(OrderConstants.FILE_WRITE_ERROR);
        }
    }
    @Override
    public List<Orders> getAllOrders(){
        return orderRepository.findAll();
    }
}
