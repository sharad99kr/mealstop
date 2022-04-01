package com.dalhousie.MealStop.cart.controller;

import com.dalhousie.MealStop.cart.contriller.CustomerCartController;
import com.dalhousie.MealStop.cart.modal.CustomerCart;
import com.dalhousie.MealStop.cart.service.CustomerCartServiceImpl;
import com.dalhousie.MealStop.cart.service.ICustomerCartService;
import com.dalhousie.MealStop.customer.modal.Customer;
import com.dalhousie.MealStop.customer.service.ICustomerService;
import com.dalhousie.MealStop.meal.model.Meal;
import com.dalhousie.MealStop.meal.service.IMealService;
import com.dalhousie.MealStop.restaurant.model.Restaurant;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerCartControllerTest
{
    @Mock
    private ICustomerCartService customerCartService;

    @Mock
    private ICustomerService customerService;

    @InjectMocks
    private CustomerCartController customerCartController;

    @Mock
    private IMealService mealService;

    private MockMvc mockMvc;

    private Customer customer;

    private Restaurant restaurant;

    private Meal meal;

    private CustomerCart customerCart;

    @BeforeEach
    void setUp()
    {
        initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerCartController).build();
        customer = new Customer("Test", "User","Test@gmail.com", "9029893443", "March 1, 1995", "911 Park Victoria Canada");
        customer.setId(1L);

        restaurant = new Restaurant("Restaurant1", 1L, "monday, tuesday","p@gmail.com", "9029893443", "911 Park Victoria");
        restaurant.setId(1L);

        meal = new Meal("ThaiMeal", "120","fat, protein", "Thai", 100);
        meal.setId(1L);

        customerCart = new CustomerCart();
    }

    @AfterEach
    void tearDown()
    {
        customer = null;
        restaurant = null;
        meal = null;
        customerCart = null;
    }

    @Test
    public void getCustomerCartPage() throws Exception
    {
        Mockito.lenient().when(customerCartService.getCustomerCart()).thenReturn(customerCart);
        Mockito.lenient().when(customerService.getCustomerDetailsFromSession()).thenReturn(customer);
        mockMvc.perform(get("/customer/cart"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("cart", customerCart))
                .andExpect(model().attribute("customer", customer));
    }

    @Test
    public void addItemsToCart() throws Exception
    {
        Mockito.lenient().when(mealService.getMealByMealId(1L)).thenReturn(meal);
        Mockito.lenient().doNothing().when(customerCartService).addMealsToCustomerCart(meal);
        mockMvc.perform(post("/customer/cart/add_item/{id}", 1L))
                .andExpect(status().isFound());
        assertEquals("redirect:/customer/cart", customerCartController.addItemsToCart(1L));
    }

    @Test
    public void removeItemsFromCart() throws Exception
    {
        Mockito.lenient().when(mealService.getMealByMealId(1L)).thenReturn(meal);
        Mockito.lenient().doNothing().when(customerCartService).removeMealsFromCustomerCart(meal);
        mockMvc.perform(post("/customer/cart/remove_item/{id}", 1L))
                .andExpect(status().isFound());
        assertEquals("redirect:/customer/cart", customerCartController.removeItemsFromCart(1L));
    }
}
