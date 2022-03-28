package com.dalhousie.MealStop.favorites.service;

import com.dalhousie.MealStop.Meal.repository.MealRepository;
import com.dalhousie.MealStop.Restaurant.model.Restaurant;
import com.dalhousie.MealStop.Restaurant.service.IRestaurantService;
import com.dalhousie.MealStop.Restaurant.service.RestaurantServiceImplementation;
import com.dalhousie.MealStop.customer.modal.Customer;
import com.dalhousie.MealStop.customer.service.CustomerServiceImplementation;
import com.dalhousie.MealStop.customer.service.ICustomerService;
import com.dalhousie.MealStop.favorites.modal.CustomerFavorites;
import com.dalhousie.MealStop.favorites.repository.CustomerFavoritesRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class CustomerFavoriteServiceImplementationTest {

    @Mock
    private CustomerFavoritesRepository customerFavoritesRepository;

    @Mock
    private ICustomerService customerService;

    @Mock
    private IRestaurantService restaurantService;

    @InjectMocks
    @Autowired
    private CustomerFavoriteServiceImplementation customerFavoriteServiceImplementation;

    private CustomerFavorites customerFavorites1;
    private List<CustomerFavorites> customerFavoritesList;
    private Restaurant restaurant1;
    private Customer customer;

    @BeforeEach
    void setUp() {
        restaurant1 = new Restaurant("Restaurant1", 1L, "monday, tuesday","p@gmail.com", "9029893443", "911 Park Victoria");
        restaurant1.setId(1L);
        customer = new Customer("TestCustomer", "LTest","Test@gmail.com");
        customer.setId(1L);
        customer.setTokens(100);
        customerFavorites1 = new CustomerFavorites(customer, restaurant1);
        customerFavoritesList = new ArrayList<>();
        customerFavoritesList.add(customerFavorites1);
    }

    @AfterEach
    void tearDown() {
        restaurant1 = null;
        customer = null;
        customerFavorites1 = null;
    }

    @Test
    void getCustomerFavorites() {
        Mockito.lenient().doReturn(customer).when(customerService).getCustomerDetailsFromSession();
        Mockito.lenient().when(customerFavoritesRepository.findByCustomer(customer)).thenReturn(customerFavoritesList);
        assertEquals(1, customerFavoriteServiceImplementation.getCustomerFavorites().size());
    }

    @Test
    void addRestaurantToCustomerFavorites() {
        ICustomerService customerServiceSpy = spy(customerService);
        Mockito.lenient().doReturn(customer).when(customerServiceSpy).getCustomerDetailsFromSession();
        IRestaurantService restaurantServiceSpy = spy(restaurantService);
        Mockito.lenient().doReturn(restaurant1).when(restaurantServiceSpy).getRestaurantById(1L);
        Mockito.lenient().when(customerFavoritesRepository.findByCustomerAndRestaurant(customer, restaurant1)).thenReturn(customerFavorites1);
        customerFavoriteServiceImplementation.addRestaurantToCustomerFavorites(1L);
        customerFavoritesRepository.save(customerFavorites1);
        verify(customerFavoritesRepository,times(2)).save(any());
    }

    @Test
    void deleteCustomerFavoriteById() {
        doNothing().when(customerFavoritesRepository).deleteById(1L);
        customerFavoriteServiceImplementation.deleteCustomerFavoriteById(1L);
        verify(customerFavoritesRepository, times(1)).deleteById(1L);
    }
}