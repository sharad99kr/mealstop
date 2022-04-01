package com.dalhousie.MealStop.orders.repository;

import com.dalhousie.MealStop.common.OrderConstants;
import com.dalhousie.MealStop.orders.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Orders,Long> {

    //this method definition finds list of orders based on the status provided
    public List<Orders> findByStatus(int status);

    //this method definition finds list of orders that were ordered by a particular using customer id
    public List<Orders> findByCustomerId(long customerid);

    //this method definition is used to get order by order id from database
    public Orders findById(long id);


    public List<Orders> findByRestaurantIdAndStatus(long restaurantId,int status);

    public List<Orders> findByCustomerIdAndStatus(long customerId,int status);


    //this method definition is used to get order by restaurant id from the database
    public List<Orders> findByRestaurantId(long restaurantId);

    //this method definition is used to udate the status of order by using order id

    @Query(value = "UPDATE orders SET status = ?2 where id=?1",nativeQuery = true)
    @Modifying
    @Transactional
    public void updateOrdersById(long orderId, int status);



    //customer query to find most ordered meal by a customer from a restaurant
    @Query(value = OrderConstants.MOST_ORDERED_MEAL_BY_CUSTOMER_FROM_RESTAURANT, nativeQuery = true)
    List<Long> findByCustomerIdAndRestaurantId(Long customerId,Long restaurantId);

    //customer query to find most ordered meal by a customer
    @Query(value = OrderConstants.MOST_ORDERED_MEAL_BY_CUSTOMER, nativeQuery = true)
    List<Long> findAllByCustomerId(Long customerId);

    //customer query to find most ordered meal from a restaurant
    @Query(value = OrderConstants.MOST_ORDERED_MEAL_FROM_RESTAURANT, nativeQuery = true)
    List<Long> findAllByRestaurantId(Long restaurant_id);

    @Query(value = OrderConstants.MONTHLY_SALES_OF_RESTAURANT, nativeQuery = true)
    List<Orders> findAllByRestaurantIdandYear(Long restaurant_id, int year);

}
