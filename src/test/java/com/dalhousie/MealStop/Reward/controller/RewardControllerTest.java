package com.dalhousie.MealStop.Reward.controller;

import com.dalhousie.MealStop.Reward.service.RewardService;
import com.dalhousie.MealStop.customer.builder.CustomerBuilder;
import com.dalhousie.MealStop.customer.modal.Customer;
import com.dalhousie.MealStop.customer.service.CustomerServiceImplementation;
import com.dalhousie.MealStop.orders.service.OrderService;
import com.dalhousie.MealStop.restaurant.model.Restaurant;
import com.dalhousie.MealStop.review.controller.CustomerReviewController;
import com.dalhousie.MealStop.review.modal.CustomerReview;
import com.dalhousie.MealStop.user.entity.User;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RewardControllerTest {


    @Mock
    private CustomerServiceImplementation customerService;

    private long mockCustomerId;

    private MockMvc mockMvc;

    @Autowired
    @Mock
    private RewardService rewardService;

    @InjectMocks
    RewardController rewardController;

    private Customer customer;

    private CustomerBuilder customerBuilder;

    @BeforeEach
    void setUp()
    {
        initMocks(this);
        mockCustomerId=1L;
        this.mockMvc = MockMvcBuilders.standaloneSetup(rewardController).build();
        customerBuilder = new CustomerBuilder();
        customerBuilder.setId(1L);
        customerBuilder.setFirstName("Shathish");
        customerBuilder.setLastName("Annamalai");
        customerBuilder.setEmail("abc@gmail.com");
        customerBuilder.setAddress("Halifax, NS, Canada");
        customerBuilder.setMobileNumber("9898989898");
        customerBuilder.setDateOfBirth("March 10, 2021");
        customerBuilder.setTokens(10);
        customer = customerBuilder.buildCustomer();
    }



    @Test
    void redeemPoints() throws Exception {


//        Mockito.lenient().when(customerService.getCustomerDetailsFromSession()).thenReturn(customer);
//        Customer cus=customerService.getCustomerDetailsFromSession();
//        doThrow(new RuntimeException()).when(rewardService).redeemRewardPoints(cus.getId());
//
//        Model model=null;
//        mockMvc.perform(post("/reward/redeem", model))
//                .andExpect(status().isBadRequest());
//
//        assertEquals("redirect:/customer/profile", rewardController.redeemPoints(model));
    }
}