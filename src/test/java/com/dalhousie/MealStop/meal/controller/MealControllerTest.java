package com.dalhousie.MealStop.meal.controller;

import com.dalhousie.MealStop.meal.model.Meal;
import com.dalhousie.MealStop.meal.service.IMealService;
import com.dalhousie.MealStop.restaurant.model.Restaurant;
import com.dalhousie.MealStop.restaurant.service.IRestaurantService;
import com.dalhousie.MealStop.tests_support.TestsSupport;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class MealControllerTest {

    @Mock
    private IMealService mealService;

    @Mock
    private IRestaurantService restaurantService;

    @Mock
    private BindingResult mockBindingResult;


    @InjectMocks
    MealController mealController;

    private MockMvc mockMvc;

    private Restaurant restaurant1;
    private List<Restaurant> restaurantList;
    private Meal meal1;
    List<Meal> mealList;

    private final TestsSupport testsSupport = new TestsSupport();

    @BeforeEach
    void setUp() {
        initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(mealController).build();
        meal1 = testsSupport.createDummyMeal();
        meal1.setId(1L);
        restaurant1 = testsSupport.createDummyRestaurant();
        restaurant1.setId(1L);
        meal1.setRestaurant(restaurant1);
        restaurantList = new ArrayList<>();
        restaurantList.add(restaurant1);
        mealList = new ArrayList<>();
        mealList.add(meal1);
    }

    @AfterEach
    void tearDown() {
        restaurant1 = null;
        restaurantList = null;
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
                .andExpect(status().isOk());
        Model model = null;
        assertEquals("redirect:/restaurant/get_meal/1", mealController.addMeal(meal1, mockBindingResult,1L, model));
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
    void updateMeal() {
        Mockito.lenient().when(mealService.updateMeal(1L,meal1)).thenReturn(meal1);
        Mockito.lenient().when(mockBindingResult.hasErrors()).thenReturn(false);
        Model model = null;
        assertEquals("redirect:/restaurant/get_meal/1", mealController.updateMeal(meal1, mockBindingResult, 1L, model));
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