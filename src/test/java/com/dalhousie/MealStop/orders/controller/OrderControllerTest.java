package com.dalhousie.MealStop.orders.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)

class OrderControllerTest {

    @InjectMocks
    OrderController orderController;


    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addNewOrders() {
    }

    @Test
    void customerOrdersNew() {
    }

    @Test
    void customerProcessedOrders() {
    }

    @Test
    void getAllCancelledOrders() {
    }

    @Test
    void getCancelledOrdersPayload() {
    }

    @Test
    void restaurantOrders() {
    }

    @Test
    void restaurantActiveOrders() {
    }

    @Test
    void ngoSendCancelledOrders() {
    }

    @Test
    void ngoAcceptedOrders() {
    }

    @Test
    void getRestaurantOrdersList() {
    }

    @Test
    void customerAllOrders() {
    }

    @Test
    void customerOrders() {
    }

    @Test
    void updateOrder() {
    }

    @Test
    void cancelOrder() {
    }

    @Test
    void geOrdersPayloadForCustomers() {
    }

    @Test
    void restaurantUpdateOrder() {
    }

    @Test
    void report() {
    }

    @Test
    void generateCsv() {

    }

    @Test
    void foodDelivered() throws Exception {

        mockMvc.perform(get("/orders/Enjoy"))
                .andExpect(status().isOk());


    }
}