package com.dalhousie.MealStop.reward.controller;

import com.dalhousie.MealStop.reward.service.RewardService;
import com.dalhousie.MealStop.customer.builder.CustomerBuilder;
import com.dalhousie.MealStop.customer.model.Customer;
import com.dalhousie.MealStop.customer.service.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

class RewardControllerTest {


    @Mock
    private CustomerServiceImpl customerService;

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

        Mockito.lenient().when(customerService.getCustomerDetailsFromSession()).thenReturn(customer);
        long id=customerService.getCustomerDetailsFromSession().getId();
        when(rewardService.redeemRewardPoints(id)).thenReturn(1);
        mockMvc.perform(post("/reward/redeem"))
                .andExpect(MockMvcResultMatchers.status().isFound());

        verify(rewardService, times(1)).redeemRewardPoints(id);
    }
}