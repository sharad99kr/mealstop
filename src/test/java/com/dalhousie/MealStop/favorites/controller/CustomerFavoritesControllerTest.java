package com.dalhousie.MealStop.favorites.controller;

import com.dalhousie.MealStop.customer.builder.CustomerBuilder;
import com.dalhousie.MealStop.customer.modal.Customer;
import com.dalhousie.MealStop.favorites.modal.CustomerFavorites;
import com.dalhousie.MealStop.favorites.service.CustomerFavoriteService;
import com.dalhousie.MealStop.restaurant.model.Restaurant;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerFavoritesControllerTest
{
    @Mock
    private CustomerFavoriteService customerFavoriteService;

    @InjectMocks
    CustomerFavoritesController customerFavoritesController;

    private MockMvc mockMvc;

    private Customer customer;

    private Restaurant restaurant;

    private CustomerFavorites customerFavorites;

    private List<CustomerFavorites> customerFavoritesList;

    private CustomerBuilder customerBuilder;

    @BeforeEach
    void setUp()
    {
        initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(customerFavoritesController).build();

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

        restaurant = new Restaurant("Restaurant1", 1L, "monday, tuesday","p@gmail.com", "9029893443", "911 Park Victoria");
        restaurant.setId(1L);

        customerFavorites = new CustomerFavorites(customer, restaurant);
        customerFavoritesList = new ArrayList<>();
        customerFavoritesList.add(customerFavorites);
    }

    @AfterEach
    void tearDown()
    {
        customer = null;
        restaurant = null;
        customerFavorites = null;
    }

    @Test
    void getFavoritePage() throws Exception
    {
        Mockito.lenient().when(customerFavoriteService.getCustomerFavorites()).thenReturn(customerFavoritesList);
        mockMvc.perform(get("/customer/favorite"))
                .andExpect(status().isOk());
        verify(customerFavoriteService, times(1)).getCustomerFavorites();
        verifyZeroInteractions(customerFavoriteService);
    }

    @Test
    void addCustomerFavorite() throws Exception
    {
        Mockito.lenient().doNothing().when(customerFavoriteService).addRestaurantToCustomerFavorites(1L);
        Mockito.lenient().when(customerFavoriteService.getCustomerFavorites()).thenReturn(customerFavoritesList);

        mockMvc.perform(post("/customer/add_favorite/{id}", 1L)).andExpect(status().isFound());
        assertEquals("redirect:/customer/favorite", customerFavoritesController.addCustomerFavorite( 1L));
    }

    @Test
    void removeCustomerFavorite() throws Exception
    {
        Mockito.lenient().doNothing().when(customerFavoriteService).deleteCustomerFavoriteById(1L);
        mockMvc.perform(post("/customer/remove_favorite/{id}", 1L)).andExpect(status().isFound());
        assertEquals("redirect:/customer/favorite", customerFavoritesController.removeCustomerFavorite(1L));
    }
}
