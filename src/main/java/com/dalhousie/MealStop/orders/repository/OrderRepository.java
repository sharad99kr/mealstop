package com.dalhousie.MealStop.orders.repository;

import com.dalhousie.MealStop.orders.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders,Long> {
    public List<Orders> findByCustomerId(long customerid);
    public Orders findById(long id);
    public List<Orders> findByRestaurantId(long restaurantId);

}
