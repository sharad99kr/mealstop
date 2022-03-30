package com.dalhousie.MealStop.ngo;

import com.dalhousie.MealStop.customer.controller.CustomerController;
import com.dalhousie.MealStop.customer.modal.Customer;
import com.dalhousie.MealStop.customer.service.ICustomerService;
import com.dalhousie.MealStop.meal.model.Meal;
import com.dalhousie.MealStop.ngo.modal.NGO;
import com.dalhousie.MealStop.ngo.service.INGOService;
import com.dalhousie.MealStop.restaurant.model.Restaurant;
import com.dalhousie.MealStop.restaurant.service.IRestaurantService;
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

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class NGOControllerTests {

        @Mock
        private INGOService ingoService;

        @Mock
        private IRestaurantService restaurantService;

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
            mockMvc.perform(get("ngo/profile"))
                    .andExpect(status().isOk());
            verify(ingoService, times(1)).getNGODetailsFromSession();
        }

        @Test
        void getLandingPage() throws Exception
        {
            Mockito.lenient().when(ingoService.getNGODetailsFromSession()).thenReturn(ngo);
            mockMvc.perform(get("/ngo/homepage"))
                    .andExpect(status().isOk());
            verify(ingoService, times(1)).getNGODetailsFromSession();
        }


}

