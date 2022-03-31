package com.dalhousie.MealStop.restaurant.controller;

import com.dalhousie.MealStop.restaurant.model.Restaurant;
import com.dalhousie.MealStop.restaurant.service.IRestaurantService;
import com.dalhousie.MealStop.user.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class RestaurantControllerTest {

    @Mock
    private IRestaurantService restaurantService;

    @Mock
    private BindingResult mockBindingResult;

    @InjectMocks
    RestaurantController restaurantController;

    private MockMvc mockMvc;

    private Restaurant restaurant1;
    private List<Restaurant> restaurantList;
    private User mockUser;

    @BeforeEach
    void setUp() {
        initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(restaurantController).build();

        restaurant1 = new Restaurant("Restaurant1", 1L, "monday, tuesday","p@gmail.com", "9029893443", "911 Park Victoria");
        restaurant1.setId(1L);
        restaurantList = new ArrayList<>();
        restaurantList.add(restaurant1);
        mockUser = new User();
    }

    @AfterEach
    void tearDown() {
        restaurant1 = null;
        restaurantList = null;
        mockUser = null;
    }

    @Test
    void getAllRestaurants() throws Exception {
        Mockito.lenient().when(restaurantService.getAllRestaurantByUserId()).thenReturn(restaurantList);

        mockMvc.perform(get("/restaurant/get_restaurant"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("restaurants_list", restaurantList));

        verify(restaurantService, times(1)).getAllRestaurantByUserId();
    }

    @Test
    void edit() throws Exception {
        Mockito.lenient().when(restaurantService.getRestaurantById(1L)).thenReturn(restaurant1);
        mockMvc.perform(get("/restaurant/edit/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(model().attribute("restaurant", restaurant1));
        verify(restaurantService, times(1)).getRestaurantById(1L);
    }

    @Test
    void updateRestaurant() throws Exception {
        Mockito.lenient().when(restaurantService.updateRestaurant(restaurant1,1L)).thenReturn(restaurant1);
        mockMvc.perform(post("/restaurant/update_restaurant/{id}", 1L)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .requestAttr("Restaurant", restaurant1))
                .andExpect(status().isOk());
        assertEquals("redirect:/restaurant/get_restaurant/", restaurantController.updateRestaurant(restaurant1, mockBindingResult, 1L));
    }

    @Test
    void addRestaurantForm() throws Exception {
        mockMvc.perform(get("/restaurant/add_restaurant_form"))
                .andExpect(status().isOk());
        assertEquals("restaurant/add_restaurant", restaurantController.addRestaurantForm(restaurant1));
    }

    @Test
    void addRestaurant() throws Exception {
        Mockito.lenient().doNothing().when(restaurantService).addRestaurant(restaurant1);
        mockMvc.perform(post("/restaurant/add_restaurant")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .requestAttr("Restaurant", restaurant1))
                .andExpect(status().isOk());

        assertEquals("redirect:/restaurant/get_restaurant/", restaurantController.addRestaurant(restaurant1, mockBindingResult));
    }

    @Test
    void getRestaurantProfilePage() throws Exception {
        Mockito.lenient().when(restaurantService.getRestaurantUserDetailsFromSession()).thenReturn(mockUser);
        mockMvc.perform(get("/restaurant/profile"))
                .andExpect(status().isOk());
        verify(restaurantService, times(1)).getRestaurantUserDetailsFromSession();
    }
}