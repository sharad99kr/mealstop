package com.dalhousie.MealStop.orders.service;

import com.dalhousie.MealStop.orders.model.Orders;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addOrder(Orders order) {
        assertNotNull(order);
    }

    @Test
    void getAllOrders() {
    }

    @Test
    void getOrdersByCustomerID() {
    }

    @Test
    void getOrderByOrderID() {
    }

    @Test
    void getOrdersByRestaurantID() {
    }

    @Test
    void getMostOrderedMeal() {
    }

    @Test
    void getMostOrderedMealOfRestaurant() {
    }

    @Test
    void getMostOrderedMealOfCustomer() {
    }
}