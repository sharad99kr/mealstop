package com.dalhousie.MealStop.cart.service;

import com.dalhousie.MealStop.Meal.model.Meal;
import com.dalhousie.MealStop.cart.modal.CustomerCart;
import com.dalhousie.MealStop.customer.modal.Customer;
import com.dalhousie.MealStop.customer.service.ICustomerService;
import com.dalhousie.MealStop.user.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class CustomerCartServiceTest {
    @Mock
    private ICustomerService customerService;

    @InjectMocks
    CustomerCartService customerCartService;

    @InjectMocks
    private HashMap<Long, CustomerCart> mockCustomersCartMap;

    private CustomerCart customerCart;
    private Meal meal1;
    private ArrayList<Meal> mealList;

    @BeforeEach
    void setUp()
    {
        mockCustomersCartMap = new HashMap<>();
        customerCart = new CustomerCart();
        meal1 = new Meal("ThaiMeal", "120","fat, protein", "Thai", 100);
        meal1.setId(1L);
        mealList = new ArrayList<>();
        mealList.add(meal1);
        customerCart.setCartItems(mealList);
        mockCustomersCartMap.put(1L, customerCart);
    }

    @AfterEach
    void tearDown()
    {
        mockCustomersCartMap = null;
        customerCart= null;
        meal1 = null;
        mealList =null;
    }

    @Test
    void getCustomerCart() {
        Mockito.lenient().when(customerService.getLoggedInCustomerId()).thenReturn(1L);
        assertNotNull(customerCartService.getCustomerCart());
    }

    @Test
    void addMealsToCustomerCart() {
        Mockito.lenient().when(customerService.getLoggedInCustomerId()).thenReturn(1L);
        customerCartService.addMealsToCustomerCart(meal1);
        assertNotNull(customerCartService.getCustomerCart());
    }

    @Test
    void removeMealsFromCustomerCart() {
        Mockito.lenient().when(customerService.getLoggedInCustomerId()).thenReturn(1L);
        customerCartService.removeMealsFromCustomerCart(meal1);
        customerCartService.clearCustomerCart();
        assertEquals(0,customerCartService.getCustomerCart().getCartItems().size());
    }

    @Test
    void clearCustomerCart() {
        Mockito.lenient().when(customerService.getLoggedInCustomerId()).thenReturn(1L);
        customerCartService.addMealsToCustomerCart(meal1);
        customerCartService.clearCustomerCart();
        assertEquals(0,customerCartService.getCustomerCart().getCartItems().size());
    }

}