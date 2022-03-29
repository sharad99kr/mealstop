package com.dalhousie.MealStop.Meal.controller;

import com.dalhousie.MealStop.Meal.model.Meal;
import com.dalhousie.MealStop.Meal.service.IMealService;
import com.dalhousie.MealStop.Restaurant.controller.RestaurantController;
import com.dalhousie.MealStop.Restaurant.model.Restaurant;
import com.dalhousie.MealStop.Restaurant.service.IRestaurantService;
import com.dalhousie.MealStop.user.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MealControllerTest {

    @Mock
    private IMealService mealService;

    @Mock
    private IRestaurantService restaurantService;

    @InjectMocks
    MealController mealController;

    private MockMvc mockMvc;

    private Restaurant restaurant1;
    private List<Restaurant> restaurantList;
    private User mockUser;
    private Meal meal1;
    List<Meal> mealList;

    @BeforeEach
    void setUp() {
        initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(mealController).build();
        meal1 = new Meal("ThaiMeal", "120","fat, protein", "Thai", 100);
        meal1.setId(1L);
        restaurant1 = new Restaurant("Restaurant1", 1L, "monday, tuesday","p@gmail.com", "9029893443", "911 Park Victoria");
        restaurant1.setId(1L);
        meal1.setRestaurant(restaurant1);
        restaurantList = new ArrayList<>();
        restaurantList.add(restaurant1);
        mockUser = new User();
        mealList = new ArrayList<>();
        mealList.add(meal1);
    }

    @AfterEach
    void tearDown() {
        restaurant1 = null;
        restaurantList = null;
        mockUser = null;
        meal1 = null;
        mealList = null;
    }

    @Test
    void addMealForm() throws Exception {
        mockMvc.perform(get("/restaurant/add_meal_form/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(model().attribute("restaurant", 1L));
        verifyZeroInteractions(mealService);
    }

    @Test
    void addMeal() throws Exception {
        Mockito.lenient().doNothing().when(mealService).addMeal(meal1);
        Mockito.lenient().when(restaurantService.getRestaurantById(1L)).thenReturn(restaurant1);
        mockMvc.perform(post("/restaurant/add_meal/{id}", 1L)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .requestAttr("meal", meal1))
                .andExpect(status().isFound());

        assertEquals("redirect:/restaurant/get_meal/1", mealController.addMeal(meal1, 1L));
    }

    @Test
    void edit() throws Exception {
        Mockito.lenient().when(mealService.getMealByMealId(1L)).thenReturn(meal1);
        mockMvc.perform(get("/restaurant/editMeal/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(model().attribute("restaurant", 1L))
                .andExpect(model().attribute("meal", meal1));
        verify(mealService, times(1)).getMealByMealId(1L);
        verifyZeroInteractions(mealService);
    }

    @Test
    void updateMeal() throws Exception {
        Mockito.lenient().when(mealService.updateMeal(1L,meal1)).thenReturn(meal1);
        assertEquals("redirect:/restaurant/get_meal/1", mealController.updateMeal(meal1, 1L));
    }

    @Test
    void getAllMeals() throws Exception {
        Mockito.lenient().when(mealService.getAllMealsByRestaurantId(1L)).thenReturn(mealList);
        mockMvc.perform(get("/restaurant/get_meal/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(model().attribute("restaurant", 1L))
                .andExpect(model().attribute("meal_list", mealList));

        verify(mealService, times(1)).getAllMealsByRestaurantId(1L);
        verifyZeroInteractions(mealService);
    }

    @Test
    void getRestaurantMeals() throws Exception {
        Mockito.lenient().when(mealService.getAllMealsByRestaurantId(1L)).thenReturn(mealList);
        mockMvc.perform(get("/customer/get_meals/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(model().attribute("restaurant", 1L))
                .andExpect(model().attribute("meal_list", mealList));

        verify(mealService, times(1)).getAllMealsByRestaurantId(1L);
        verifyZeroInteractions(mealService);
    }
}