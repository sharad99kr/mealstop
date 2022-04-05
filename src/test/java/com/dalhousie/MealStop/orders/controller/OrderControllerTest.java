package com.dalhousie.MealStop.orders.controller;

import com.dalhousie.MealStop.common.OrderConstants;
import com.dalhousie.MealStop.customer.builder.CustomerBuilder;
import com.dalhousie.MealStop.customer.model.Customer;
import com.dalhousie.MealStop.customer.service.ICustomerService;
import com.dalhousie.MealStop.meal.model.Meal;
import com.dalhousie.MealStop.meal.service.IMealService;
import com.dalhousie.MealStop.ngo.service.INGOService;
import com.dalhousie.MealStop.orders.utils.Utils;
import com.dalhousie.MealStop.orders.model.Orders;
import com.dalhousie.MealStop.orders.model.OrdersPayload;
import com.dalhousie.MealStop.orders.service.IOrderService;
import com.dalhousie.MealStop.restaurant.service.IRestaurantService;
import com.dalhousie.MealStop.tests_support.TestsSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)

class OrderControllerTest {

    @Autowired
    @InjectMocks
    OrderController orderController;

    @Mock
    IOrderService mockOrderService;


    long mockRestaurantId;

    @Autowired
    private IRestaurantService mockrestaurantService;

    @Autowired
    private IMealService mockmealService;

    @Mock
    ICustomerService mockCustomerService;

    @Mock
    INGOService ngoService;

    @Mock
    IMealService mealService;

    @Mock
    Customer mockCustomer;

    @Mock
    private CustomerBuilder customerBuilder;

    private MockMvc mockMvc;

    @Mock
    List<Orders> mock_orders;

    @Mock
    Orders mock_order;


    @Mock
    List<OrdersPayload> mock_order_list;

    @Mock
    List<OrdersPayload> mock_cancelled_order_list;

    private long mockCustomerId;

    private long mockOrderId;


    private long mockMealId;
    private long mockPaymentId;

    private long mockNgoId;

    private TestsSupport testsSupport = new TestsSupport();

    @BeforeEach
    void setUp() {
        initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
        mockOrderId=1;
        mockRestaurantId=1;
        customerBuilder = new CustomerBuilder();
        customerBuilder.setId(1L);
        customerBuilder.setFirstName("Sharad");
        customerBuilder.setLastName("kumar");
        customerBuilder.setEmail("abc@gmail.com");
        customerBuilder.setAddress("Halifax, NS, Canada");
        customerBuilder.setMobileNumber("9898989898");
        customerBuilder.setDateOfBirth("March 10, 2021");
        customerBuilder.setTokens(10);
        mockCustomer = customerBuilder.buildCustomer();

        mock_order=testsSupport.createDummyOrder(OrderConstants.CANCELLED);

        Orders order=testsSupport.createDummyOrder(OrderConstants.CANCELLED);
        mock_orders.add(order);
        order=testsSupport.createDummyOrder(OrderConstants.CANCELLED);
        mock_orders.add(order);

        mock_order_list=new ArrayList<>();
        OrdersPayload payload=new OrdersPayload();
        payload.orderId=1;
        payload.mealName = "biryani";
        payload.restaurantName="stoner";
        payload.amount = 2;
        payload.date = "20220304";
        payload.status = "3";
        payload.imageUrl=Utils.getUrls().get(Utils.getRandomNumberUsingInts(0,Utils.getUrls().size()));
        mock_order_list.add(payload);

        mock_cancelled_order_list=new ArrayList<>();
        OrdersPayload payload2=new OrdersPayload();
        payload.orderId=1;
        payload.mealName = "biryani";
        payload.restaurantName="stoner";
        payload.amount = 2;
        payload.date = "20220304";
        payload.status = "3";
        payload.imageUrl=Utils.getUrls().get(Utils.getRandomNumberUsingInts(0,Utils.getUrls().size()));
        mock_cancelled_order_list.add(payload);
    }


    @Test
    void allCustomerOrders() throws Exception {

        Mockito.lenient().when(mockCustomerService.getCustomerDetailsFromSession()).thenReturn(mockCustomer);
        long mockCustomerId=mockCustomerService.getCustomerDetailsFromSession().getId();
        OrderController obj=Mockito.spy(orderController);
        Mockito.lenient().doReturn(mock_order_list).when(obj).geOrdersPayloadForCustomers(mockCustomerId,OrderConstants.ACTIVE);

        mockMvc.perform(get("/orders/customer_orders_all", 1L))
                .andExpect(status().isOk());

        verify(mockCustomerService, times(2)).getCustomerDetailsFromSession();
    }

    private List<OrdersPayload> geOrdersPayloadForCustomers(long mockCustomerId, int active) {

        return mock_order_list;
    }

    @Test
    void customerProcessedOrders() throws Exception {

        Mockito.lenient().when(mockCustomerService.getCustomerDetailsFromSession()).thenReturn(mockCustomer);
        long mockCustomerId=mockCustomerService.getCustomerDetailsFromSession().getId();
        OrderController obj=Mockito.spy(orderController);
        Mockito.lenient().doReturn(mock_order_list).when(obj).geOrdersPayloadForCustomers(mockCustomerId,OrderConstants.PROCESSED);

        mockMvc.perform(get("/orders/customer_processed_orders", 1L))
                .andExpect(status().isOk());

        verify(mockCustomerService, times(2)).getCustomerDetailsFromSession();
    }

    @Test
    void getAllCancelledOrders() throws Exception {
        Mockito.lenient().when(mockOrderService.getAllCanceledOrders()).thenReturn(mock_orders);
        //Mockito.lenient().when(getCancelledOrdersPayload(mock_orders)).thenReturn(mock_cancelled_order_list);

        OrderController obj=Mockito.spy(orderController);

        Mockito.lenient().doReturn(mock_cancelled_order_list).when(obj).getCancelledOrdersPayload(mock_orders);


        mockMvc.perform(get("/orders/cancelled_orders"))
                .andExpect(status().isOk());

        verify(mockOrderService, times(1)).getAllCanceledOrders();
    }

    private List<OrdersPayload> getCancelledOrdersPayload( List<Orders> listOrders){
        return mock_cancelled_order_list;
    }


    @Test
    void updateOrder() throws Exception {
        Mockito.lenient().when(mockOrderService.updateOrderStatus(mockOrderId,OrderConstants.DELIVERED)).thenReturn(true);
        mockMvc.perform(post("/updateOrder/{id}", 1L)).andExpect(status().isOk());

        verify(mockOrderService, times(1)).updateOrderStatus(mockOrderId,OrderConstants.DELIVERED);

    }

    @Test
    void cancelOrder() throws Exception {


        List<OrdersPayload> order_list=new ArrayList<>();
        OrdersPayload payload=new OrdersPayload();
        payload.orderId=mockOrderId;
        payload.mealName = "meal";
        payload.restaurantName="restaurant";
        payload.amount = 1;
        payload.date = "20220304";
        payload.status = "Active";
        payload.imageUrl=Utils.getUrls().get(Utils.getRandomNumberUsingInts(0,Utils.getUrls().size()));
        order_list.add(payload);
        Mockito.lenient().when(mockOrderService.updateOrderStatus(mockOrderId,OrderConstants.CANCELLED)).thenReturn(true);
        Mockito.lenient().when(mockOrderService.getOrderByOrderID(mockOrderId)).thenReturn(mock_order);
        TestsSupport testsSupport = new TestsSupport();
        Meal meal = testsSupport.createDummyMeal();
        meal.setId(1L);
        Mockito.lenient().when(mealService.getMealByMealId(1L)).thenReturn(meal);
        Mockito.lenient().doNothing().when(ngoService).sendCancelledOrderNotification(any());
        OrderController obj=Mockito.spy(orderController);
        Mockito.lenient().doReturn(order_list).when(obj).geOrdersPayloadForCustomers(mockCustomerId,OrderConstants.ACTIVE);



        mockMvc.perform(get("/cancelOrder/{id}",mockOrderId))
                .andExpect(status().isOk());
        verify(mockOrderService, times(1)).updateOrderStatus(mockOrderId,OrderConstants.CANCELLED);

    }


    @Test
    void restaurantUpdateOrder() throws Exception {

        Mockito.lenient().when(mockOrderService.updateOrderStatus(mockOrderId,OrderConstants.PROCESSED)).thenReturn(true);
        Mockito.lenient().when(mockOrderService.getOrderByOrderID(mockOrderId)).thenReturn(mock_order);
        mockMvc.perform(post("/restaurantUpdateOrder/{id}", 1L)).andExpect(status().isFound());
        verify(mockOrderService, times(1)).updateOrderStatus(mockOrderId,OrderConstants.PROCESSED);

    }
}