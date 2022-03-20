package com.dalhousie.MealStop.orders.service;

import com.dalhousie.MealStop.orders.Constants.Constants;
import com.dalhousie.MealStop.orders.model.Orders;
import com.dalhousie.MealStop.orders.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.naming.NoPermissionException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class OrdersServiceTest {


    @Mock
    private OrderRepository mockOrderRepository;

    @Mock
    private Orders mockOrder;

    @Mock
    private List<Orders> mockOrders;

    @Mock
    private List<Orders> mockCustomerOrders;

    @Mock
    private List<Orders> mockRestaurantOrders;


    @Mock
    private List<Orders> mockCancelledOrders;

    @Mock
    private List<Orders> mockDeliveredOrders;


    @Mock
    private int mockCancelledStatus;

    @Mock
    private int mockDeliveredStatus;

    @Mock
    private int mockYear;

    @Mock
    private long mockCustomerId;

    @Mock
    private long mockOrderId;

    @Mock
    private long mockRestaurantId;

    @Mock
    private List<Long> mockMealIds;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);

        mockCancelledStatus= Constants.CANCELLED;
        mockDeliveredStatus=Constants.DELIVERED;

        Mockito.when(mockOrderRepository.save(mockOrder)).thenReturn(mockOrder);
        Mockito.when(mockOrderRepository.findAll()).thenReturn(mockOrders);
        Mockito.when(mockOrderRepository.findByStatus(mockCancelledStatus)).thenReturn(mockCancelledOrders);
        Mockito.when(mockOrderRepository.findByCustomerId(mockCustomerId)).thenReturn(mockCustomerOrders);
        Mockito.when(mockOrderRepository.findById(mockOrderId)).thenReturn(mockOrder);
        Mockito.when(mockOrderRepository.findByRestaurantIdAndStatus(mockRestaurantId,mockDeliveredStatus)).thenReturn(mockDeliveredOrders);
        Mockito.when(mockOrderRepository.findByCustomerIdAndStatus(mockCustomerId,mockCancelledStatus)).thenReturn(mockCancelledOrders);

        Mockito.when(mockOrderRepository.findByRestaurantId(mockRestaurantId)).thenReturn(mockOrders);
        Mockito.doThrow(new NoPermissionException()).when(mockOrderRepository).updateOrdersById(mockOrderId,mockDeliveredStatus);

        Mockito.when(mockOrderRepository.findByCustomerIdAndRestaurantId(mockCustomerId,mockRestaurantId)).thenReturn(mockMealIds);
        Mockito.when(mockOrderRepository.findAllByCustomerId(mockCustomerId)).thenReturn(mockMealIds);
        Mockito.when(mockOrderRepository.findAllByRestaurantId(mockRestaurantId)).thenReturn(mockMealIds);
        Mockito.when(mockOrderRepository.findAllByRestaurantIdandYear(mockRestaurantId,mockYear)).thenReturn(mockDeliveredOrders);

    }

    @Test
    void addOrder() {
    }

    @Test
    void updateOrderStatus() {
    }

    @Test
    void getAllOrders() {
    }

    @Test
    void getAllCanceledOrders() {
    }

    @Test
    void getCustomerOrdersWithStatus() {
    }

    @Test
    void getRestaurantOrdersWithStatus() {
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

    @Test
    void getMonthlyReportofRestaurant() {
    }
}