package com.dalhousie.MealStop.ngo.controller;

import com.dalhousie.MealStop.meal.model.Meal;
import com.dalhousie.MealStop.ngo.model.NGO;
import com.dalhousie.MealStop.ngo.service.INGOService;
import com.dalhousie.MealStop.ngoorder.service.NGOOrderServiceImpl;
import com.dalhousie.MealStop.orders.service.IOrderService;
import com.dalhousie.MealStop.restaurant.model.Restaurant;
import com.dalhousie.MealStop.tests_support.TestsSupport;
import org.junit.jupiter.api.*;
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

        @Mock
        private NGOOrderServiceImpl ngoOrderService;

        @InjectMocks
        private NGOOrderServiceImpl ngoOrderServiceimpl;


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
            TestsSupport testsSupport = new TestsSupport();
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


        @Test
        void getNgoPastOrders() throws Exception
        {
            Mockito.lenient().when(ingoService.getNGODetailsFromSession()).thenReturn(ngo);
            mockMvc.perform(get("/ngo/orders/ngo_old_order"))
                    .andExpect(status().isOk());
        }


    @Test
    void ngoAcceptedOrders() throws Exception
    {
        Mockito.lenient().when(ingoService.getNGODetailsFromSession()).thenReturn(ngo);
        mockMvc.perform(get("/ngo/orders/ngo_accepted_order/1"))
                .andExpect(status().isOk());
    }


}




