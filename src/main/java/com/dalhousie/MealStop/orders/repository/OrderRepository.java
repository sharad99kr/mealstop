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


    @Query(value = OrderConstants.MONTHLY_SALES_OF_RESTAURANT, nativeQuery = true)
    List<Orders> findAllByRestaurantIdandYear(Long restaurant_id, int year);

}
