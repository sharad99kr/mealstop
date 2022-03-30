package com.dalhousie.MealStop.customer.controller;

import com.dalhousie.MealStop.Meal.model.Meal;
import com.dalhousie.MealStop.Restaurant.model.Restaurant;
import com.dalhousie.MealStop.Restaurant.service.IRestaurantService;
import com.dalhousie.MealStop.customer.modal.Customer;
import com.dalhousie.MealStop.customer.service.ICustomerService;
import com.dalhousie.MealStop.user.entity.User;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControllerTest
{
    @Mock
    private ICustomerService customerService;

    @Mock
    private IRestaurantService restaurantService;

    @InjectMocks
    CustomerController customerController;

    private MockMvc mockMvc;

    private Customer customer;

    private Restaurant restaurant;

    private Meal meal;

    private List<Restaurant> restaurantList;

    private List<Meal> mealList;

    @BeforeEach
    void setUp()
    {
        initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
        customer = new Customer("Test", "User","Test@gmail.com");
        customer.setId(1L);

        restaurant = new Restaurant("Restaurant1", 1L, "monday, tuesday","p@gmail.com", "9029893443", "911 Park Victoria");
        restaurant.setId(1L);

        meal = new Meal("ThaiMeal", "120","fat, protein", "Thai", 100);
        meal.setId(1L);
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
        mockMvc.perform(get("/customer/profile"))
                .andExpect(status().isOk());
        verify(customerService, times(1)).getCustomerDetailsFromSession();
        verifyZeroInteractions(customerService);
    }

    @Test
    void getLandingPage() throws Exception
    {
        Mockito.lenient().when(customerService.getCustomerDetailsFromSession()).thenReturn(customer);
        mockMvc.perform(get("/customer/homepage"))
                .andExpect(status().isOk());
        verify(customerService, times(1)).getCustomerDetailsFromSession();
        verifyZeroInteractions(customerService);
    }

    @Test
    void searchRestaurants() throws Exception
    {
        Mockito.lenient().when(restaurantService.getAvailableRestaurants(any(), any())).thenReturn(restaurantList);
        Mockito.lenient().when(restaurantService.getRecommendedMealForCustomer(any())).thenReturn(mealList);

        mockMvc.perform(get("/customer/search-restaurant"))
                .andExpect(status().isOk());
        verify(restaurantService, times(1)).getAvailableRestaurants(any(), any());
        verify(restaurantService, times(1)).getRecommendedMealForCustomer(any());
        verifyZeroInteractions(restaurantService);
    }
}
