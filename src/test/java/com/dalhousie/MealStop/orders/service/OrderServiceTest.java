package com.dalhousie.MealStop.orders.service;

import com.dalhousie.MealStop.customer.service.CustomerServiceImplementation;
import com.dalhousie.MealStop.common.OrderConstants;
import com.dalhousie.MealStop.orders.model.Orders;
import com.dalhousie.MealStop.orders.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;


import java.util.List;

import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Autowired
    @InjectMocks
    private OrderService orderService;

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

    private int mockActiveStatus;

    private int mockProcessedStatus;

    private int mockCancelledStatus;


    private int mockDeliveredStatus;

    private int mockYear;

    private long mockCustomerId;

    private long mockOrderId;


    private long mockMealId;

    private long mockRestaurantId;

    private long mockPaymentId;


    private long mockAmount;


    @Mock
    private List<Long> mockMealIds;

    @Mock
    private CustomerServiceImplementation customerService;


    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockAmount=10;
        mockCancelledStatus= OrderConstants.CANCELLED;
        mockDeliveredStatus= OrderConstants.DELIVERED;
        mockActiveStatus= OrderConstants.ACTIVE;
        mockProcessedStatus= OrderConstants.PROCESSED;
        mockCustomerId=1;
        mockRestaurantId=1;
        mockMealId=1;

        mockOrder =new Orders(mockCustomerId,mockRestaurantId,mockMealId,mockPaymentId,mockAmount,mockActiveStatus);
        mockOrders.add(mockOrder);
        mockOrder =new Orders(2,2,2,2,2,mockActiveStatus);
        mockOrders.add(mockOrder);

        Orders order=new Orders(mockCustomerId,mockRestaurantId,mockMealId,mockPaymentId,mockAmount,mockActiveStatus);
        mockCustomerOrders.add(order);
        order=new Orders(1,2,2,2,2,mockActiveStatus);
        mockCustomerOrders.add(order);

        Orders order2=new Orders(mockCustomerId,mockRestaurantId,mockMealId,mockPaymentId,mockAmount,mockCancelledStatus);
        mockCancelledOrders.add(order2);
        order2=new Orders(1,2,2,2,2,mockCancelledStatus);
        mockCancelledOrders.add(order2);


        Orders order3=new Orders(mockCustomerId,mockRestaurantId,mockMealId,mockPaymentId,mockAmount,mockActiveStatus);
        mockRestaurantOrders.add(order3);
        order3=new Orders(1,1,2,2,2,mockActiveStatus);
        mockRestaurantOrders.add(order3);

        when(mockOrderRepository.save(mockOrder)).thenReturn(mockOrder);
        when(mockOrderRepository.findAll()).thenReturn(mockOrders);
        when(mockOrderRepository.findByStatus(mockCancelledStatus)).thenReturn(mockCancelledOrders);
        when(mockOrderRepository.findByCustomerId(mockCustomerId)).thenReturn(mockCustomerOrders);
        when(mockOrderRepository.findById(mockOrderId)).thenReturn(mockOrder);
        when(mockOrderRepository.findByRestaurantIdAndStatus(mockRestaurantId,mockDeliveredStatus)).thenReturn(mockDeliveredOrders);
        when(mockOrderRepository.findByCustomerIdAndStatus(mockCustomerId,mockCancelledStatus)).thenReturn(mockCancelledOrders);

        when(mockOrderRepository.findByRestaurantId(mockRestaurantId)).thenReturn(mockOrders);
        //Mockito.doThrow(new NoPermissionException()).when(mockOrderRepository).updateOrdersById(mockOrderId,mockDeliveredStatus);

        when(mockOrderRepository.findByCustomerIdAndRestaurantId(mockCustomerId,mockRestaurantId)).thenReturn(mockMealIds);
        when(mockOrderRepository.findAllByCustomerId(mockCustomerId)).thenReturn(mockMealIds);
        when(mockOrderRepository.findAllByRestaurantId(mockRestaurantId)).thenReturn(mockMealIds);
        when(mockOrderRepository.findAllByRestaurantIdandYear(mockRestaurantId,mockYear)).thenReturn(mockDeliveredOrders);

    }

//    @BeforeEach
//    void setUp() {
//    }
//
//    @AfterEach
//    void tearDown() {
//    }
//
//    @Test
//    void createOrderFromCart() {
//    }
//
    @Test
    void addOrder() {




        when(mockOrderRepository.save(any())).thenReturn(mockOrder);
        orderService.addOrder(mockOrder);
        verify(mockOrderRepository,times(1)).save(any());

        when(customerService.getCustomerTokenCount()).thenReturn(6);
        Integer currentTokens = customerService.getCustomerTokenCount();

        when(customerService.decrementCustomerToken(3)).thenReturn(3);

        Integer decrementedToken=customerService.decrementCustomerToken(3);

        assertEquals(decrementedToken,3);




    }
//
//    @Test
//    void getOrdersForNGO() {
//    }
//
//    @Test
//    void updateOrderStatus() {
//
//    }
//
//    @Test
//    void claimedByNGO() {
//    }

    @Test
    void getAllOrders() {
        assertThat(mockOrderRepository.findAll()).isEqualTo(mockOrders);
    }

    @Test
    void getAllCanceledOrders() {

        assertThat(mockOrderRepository.findByStatus(mockCancelledStatus)).isEqualTo(mockCancelledOrders);
    }

    @Test
    void getCustomerOrdersWithStatus() {
        when(mockOrderRepository.findByCustomerIdAndStatus(mockCustomerId,mockActiveStatus)).thenReturn(mockCustomerOrders);
        assertThat(orderService.getCustomerOrdersWithStatus(mockCustomerId,mockActiveStatus)).isEqualTo(mockCustomerOrders);
    }

    @Test
    void getRestaurantOrdersWithStatus() {
        when(mockOrderRepository.findByRestaurantIdAndStatus(mockRestaurantId,mockActiveStatus)).thenReturn(mockRestaurantOrders);
        assertThat(orderService.getRestaurantOrdersWithStatus(mockRestaurantId,mockActiveStatus)).isEqualTo(mockRestaurantOrders);
    }

    @Test
    void getOrdersByCustomerID() {

        assertThat(mockOrderRepository.findByCustomerId(mockCustomerId)).isEqualTo(mockCustomerOrders);
    }

    @Test
    void getOrderByOrderID() {

        when(mockOrderRepository.findById(mockOrderId)).thenReturn(mockOrder);
        assertThat(orderService.getOrderByOrderID(mockOrderId)).isEqualTo(mockOrder);

    }

    @Test
    void getOrdersByRestaurantID() {
        when(mockOrderRepository.findByRestaurantId(mockRestaurantId)).thenReturn(mockRestaurantOrders);
        assertThat(orderService.getOrdersByRestaurantID(mockRestaurantId)).isEqualTo(mockRestaurantOrders);
    }


//    @Test
//    void getMonthlyReportofRestaurant() {
//
//
//    }
//
//    @Test
//    void writeEarningsToCsv() {
//    }
}