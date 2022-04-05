package com.dalhousie.MealStop.orders.service;

import com.dalhousie.MealStop.ngoorder.model.NGOOrder;
import com.dalhousie.MealStop.ngoorder.repository.NGOOrderRepository;
import com.dalhousie.MealStop.ngoorder.service.NGOOrderServiceImpl;
import com.dalhousie.MealStop.customer.service.CustomerServiceImpl;
import com.dalhousie.MealStop.common.OrderConstants;
import com.dalhousie.MealStop.orders.model.Orders;
import com.dalhousie.MealStop.orders.repository.OrderRepository;
import com.dalhousie.MealStop.tests_support.TestsSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;


import java.util.*;

import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Autowired
    @InjectMocks
    private OrderService orderService;


    @Mock
    private OrderRepository mockOrderRepository;

    @Mock
    private NGOOrderRepository ngoOrderRepository;

    @Mock
    private Orders mockOrder;

    @Mock
    private NGOOrder mockNGOOrder;

    @Mock
    private List<NGOOrder> mockNGOOrders;

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
    private int mockClaimedStatus;

    private int mockYear;

    private long mockCustomerId;

    private long mockOrderId;


    private long mockMealId;
    private long mockPaymentId;

    private long mockRestaurantId;

    private long mockNgoId;


    private long mockAmount;

    @Mock
    NGOOrder ngoOrder1;

    @Mock
    private List<Long> mockMealIds;

    @Mock
    private CustomerServiceImpl customerService;

    @Mock
    private NGOOrderServiceImpl mockngoOrderService;

    private TestsSupport testsSupport = new TestsSupport();

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockAmount=10;
        mockCancelledStatus= OrderConstants.CANCELLED;
        mockDeliveredStatus= OrderConstants.DELIVERED;
        mockActiveStatus= OrderConstants.ACTIVE;
        mockProcessedStatus= OrderConstants.PROCESSED;
        mockClaimedStatus=OrderConstants.CLAIMED;
        mockCustomerId=1;
        mockRestaurantId=1;
        mockMealId=1;

        mockOrder =testsSupport.createDummyOrder(mockActiveStatus);
        mockOrders.add(mockOrder);
        mockOrder=new Orders();
        mockOrder.setCustomerId(2);
        mockOrder.setRestaurantId(2);
        mockOrder.setMealId(2);
        mockOrder.setOrderAmount(2);
        mockOrder.setOrderStatus(mockActiveStatus);

        mockOrders.add(mockOrder);

        Orders order=testsSupport.createDummyOrder(mockActiveStatus);
        mockCustomerOrders.add(order);
        order=testsSupport.createDummyOrder(mockActiveStatus);
        mockCustomerOrders.add(order);

        Orders order2=testsSupport.createDummyOrder(mockCancelledStatus);
        mockCancelledOrders.add(order2);
        order2=testsSupport.createDummyOrder(mockCancelledStatus);
        mockCancelledOrders.add(order2);


        Orders order3=testsSupport.createDummyOrder(mockActiveStatus);
        mockRestaurantOrders.add(order3);
        order3=testsSupport.createDummyOrder(mockActiveStatus);
        mockRestaurantOrders.add(order3);

        mockNgoId=1;
        mockOrderId=1;
        mockNGOOrder=new NGOOrder(mockOrderId, mockNgoId, OrderConstants.CLAIMED);

        mockNGOOrders.add(mockNGOOrder);



        when(mockOrderRepository.save(mockOrder)).thenReturn(mockOrder);


    }



    @Test
    void getAllOrders(){
        when(mockOrderRepository.findAll()).thenReturn(mockOrders);
        orderService.getAllOrders();
        verify(mockOrderRepository,times(1)).findAll();
    }

    @Test
    void addOrder() {
        when(mockOrderRepository.save(any())).thenReturn(mockOrder);
        orderService.addOrder(mockOrder);
        verify(mockOrderRepository,times(1)).save(any());

    }


    @Test
    void updateOrderStatus() {

        orderService.updateOrderStatus(mockCustomerId,mockActiveStatus);
        verify(mockOrderRepository,times(1)).updateOrdersById(mockCustomerId,mockActiveStatus);
    }

    @Test
    void claimedByNGO() {

        orderService.updateOrderStatus(mockOrderId,mockClaimedStatus);
        verify(mockOrderRepository,times(1)).updateOrdersById(mockOrderId,mockClaimedStatus);
        NGOOrder ngoOrder=new NGOOrder(mockOrderId,mockNgoId,mockClaimedStatus);
        orderService.claimedByNGO(mockNgoId,mockOrderId);
        mockngoOrderService.addNGOOrder(ngoOrder);
        verify(mockngoOrderService,times(1)).addNGOOrder(ngoOrder);

    }



    @Test
    void getAllCanceledOrders() {

        when(mockOrderRepository.findByStatus(mockCancelledStatus)).thenReturn(mockCancelledOrders);
        assertThat(orderService.getAllCanceledOrders()).isEqualTo(mockCancelledOrders);

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
        when(mockOrderRepository.findByCustomerId(mockCustomerId)).thenReturn(mockCustomerOrders);
        assertThat(orderService.getOrdersByCustomerID(mockCustomerId)).isEqualTo(mockCustomerOrders);

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

    @Test
    void GetSetTest(){
        mockOrder=new Orders();
        mockOrder.setCustomerId(2);
        mockOrder.setRestaurantId(2);
        mockOrder.setMealId(2);
        mockOrder.setOrderAmount(2);
        mockOrder.setOrderStatus(2);

        assertThat(mockOrder.getOrderId()).isEqualTo(0);
        assertThat(mockOrder.getCustomerId()).isEqualTo(2);
        assertThat(mockOrder.getRestaurantId()).isEqualTo(2);
        assertThat(mockOrder.getMealId()).isEqualTo(2);
        assertThat(mockOrder.getOrderAmount()).isEqualTo(2);
        assertThat(mockOrder.getOrderStatus()).isEqualTo(2);
    }

    @Test
    void getMonthlyReportofRestaurant() {

        List<Orders> mockRestaurantOrders=new ArrayList<>();
        Orders order3=testsSupport.createDummyOrder(2);
        mockRestaurantOrders.add(order3);
        order3 = testsSupport.createDummyOrder(mockActiveStatus);
        mockRestaurantOrders.add(order3);

        Mockito.lenient().when(mockOrderRepository.findAllByRestaurantIdandYear(mockRestaurantId,2022)).thenReturn(mockRestaurantOrders);

        Map<Integer, Float> earning=orderService.getMonthlyReportofRestaurant(mockRestaurantId,2022);

        assertEquals(earning.get(3),2);

    }



    @Test
    void getOrdersForNGO(){
        mockNGOOrders=new ArrayList<>();
        mockNGOOrder=new NGOOrder(mockOrderId, mockNgoId, OrderConstants.CLAIMED);

        NGOOrder mockNGOOrder2=new NGOOrder(2, mockNgoId, OrderConstants.CLAIMED);
        mockNGOOrders.add(mockNGOOrder);
        mockNGOOrders.add(mockNGOOrder2);

        Mockito.lenient().when(ngoOrderRepository.findByNGOId (mockNgoId)).thenReturn(mockNGOOrders);

        Mockito.lenient().when(mockngoOrderService.getNGOOrderWithId(mockNgoId)).thenReturn(mockNGOOrders);
        List<Orders> orders=orderService.getOrdersForNGO(mockNgoId);
        assertEquals(orders.size(),2);
    }

}