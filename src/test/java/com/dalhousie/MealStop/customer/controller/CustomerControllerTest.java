package com.dalhousie.MealStop.customer.controller;

import com.dalhousie.MealStop.reward.service.IRewardService;
import com.dalhousie.MealStop.customer.builder.CustomerBuilder;
import com.dalhousie.MealStop.meal.model.Meal;
import com.dalhousie.MealStop.restaurant.model.Restaurant;
import com.dalhousie.MealStop.restaurant.service.IRestaurantService;
import com.dalhousie.MealStop.customer.model.Customer;
import com.dalhousie.MealStop.customer.service.ICustomerService;
import com.dalhousie.MealStop.tests_support.TestsSupport;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControllerTest
{
    @Mock
    private ICustomerService customerService;

    @Mock
    private IRestaurantService restaurantService;

    @Mock
    private IRewardService rewardService;

    @InjectMocks
    CustomerController customerController;

    private MockMvc mockMvc;

    private Customer customer;

    private Restaurant restaurant;

    private Meal meal;

    private List<Restaurant> restaurantList;

    private List<Meal> mealList;

    private CustomerBuilder customerBuilder;

    private TestsSupport testsSupport = new TestsSupport();

    @BeforeEach
    void setUp()
    {
        initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();

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

        meal = testsSupport.createDummyMeal();
        meal.setId(1L);
        restaurant = testsSupport.createDummyRestaurant();
        restaurant.setId(1L);
        meal.setRestaurant(restaurant);

        mealList = new ArrayList<>();
        mealList.add(meal);
        restaurantList = new ArrayList<>();
        restaurantList.add(restaurant);
    }

    @AfterEach
    void tearDown()
    {
        customer = null;
        meal = null;
        restaurant = null;
        mealList = null;
        restaurantList=null;
    }

    @Test
    void getCustomerProfilePage() throws Exception
    {
        Mockito.lenient().when(customerService.getCustomerDetailsFromSession()).thenReturn(customer);
        Mockito.lenient().when(rewardService.isRewardPointsRedeemable(1L)).thenReturn(false);
        mockMvc.perform(get("/customer/profile"))
                .andExpect(status().isOk());
        verify(customerService, times(1)).getCustomerDetailsFromSession();
        verify(rewardService, times(1)).isRewardPointsRedeemable(1L);
    }

    @Test
    void getLandingPage() throws Exception
    {
        Mockito.lenient().when(customerService.getCustomerDetailsFromSession()).thenReturn(customer);
        mockMvc.perform(get("/customer/homepage"))
                .andExpect(status().isOk());
        verify(customerService, times(1)).getCustomerDetailsFromSession();
    }
}
