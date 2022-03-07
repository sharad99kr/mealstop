package com.dalhousie.MealStop.orders.repository;

import com.dalhousie.MealStop.orders.Constants.Constants;
import com.dalhousie.MealStop.orders.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders,Long> {
    public List<Orders> findByCustomerId(long customerid);

    public Orders findById(long id);

    public List<Orders> findByRestaurantId(long restaurantId);

    //customer query to find most ordered meal by a customer from a restaurant
    @Query(value = Constants.MOST_ORDERED_MEAL_BY_CUSTOMER_FROM_RSTAURANT, nativeQuery = true)
    List<Long> findByCustomerIdAndRestaurantId(Long customerId,Long restaurantId);

    //customer query to find most ordered meal by a customer
    @Query(value = Constants.MOST_ORDERED_MEAL_BY_CUSTOMER, nativeQuery = true)
    List<Long> findAllByCustomerId(Long customerId);

    //customer query to find most ordered meal from a restaurant
    @Query(value = Constants.MOST_ORDERED_MEAL_FROM_RSTAURANT, nativeQuery = true)
    List<Long> findAllByRestaurantId(Long restaurant_id);
}
