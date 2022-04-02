package com.dalhousie.MealStop.ngo.controller;

import com.dalhousie.MealStop.meal.model.Meal;
import com.dalhousie.MealStop.ngo.NGOController;
import com.dalhousie.MealStop.ngo.model.NGO;
import com.dalhousie.MealStop.ngo.service.INGOService;
import com.dalhousie.MealStop.orders.service.IOrderService;
import com.dalhousie.MealStop.restaurant.model.Restaurant;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class NGOControllerTests {

        @Mock
        private INGOService ingoService;

        @Mock
        private IOrderService orderService;

        @InjectMocks
        NGOController ngoController;

        private MockMvc mockMvc;

        private NGO ngo;

        private Restaurant restaurant;

        private Meal meal;

        private List<Restaurant> restaurantList;

        private List<Meal> mealList;

        @BeforeEach
        void setUp()
        {
            initMocks(this);
            this.mockMvc = MockMvcBuilders.standaloneSetup(ngoController).build();
            ngo = new NGO("Test", "User","Test@gmail.com","999999999");
            ngo.setId(1L);

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
            ngo = null;
            meal = null;
            restaurant = null;
            mealList = null;
            restaurantList=null;
        }

        @Test
        void getCustomerProfilePage() throws Exception
        {
            Mockito.lenient().when(ingoService.getNGODetailsFromSession()).thenReturn(ngo);
            mockMvc.perform(get("/ngo/profile"))
                    .andExpect(status().isOk());
            verify(ingoService, times(1)).getNGODetailsFromSession();
        }

        @Test
        void getLandingPage() throws Exception
        {
            Mockito.lenient().when(orderService.getAllCanceledOrders()).thenReturn(new ArrayList<>());
            mockMvc.perform(get("/ngo/homepage"))
                    .andExpect(status().isOk());
            verify(orderService, times(1)).getAllCanceledOrders();
        }


}

