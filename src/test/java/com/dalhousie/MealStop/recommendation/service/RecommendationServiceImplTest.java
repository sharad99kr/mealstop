package com.dalhousie.MealStop.recommendation.service;

import com.dalhousie.MealStop.meal.model.Meal;
import com.dalhousie.MealStop.meal.service.IMealService;
import com.dalhousie.MealStop.restaurant.model.Restaurant;
import com.dalhousie.MealStop.orders.model.Orders;
import com.dalhousie.MealStop.orders.service.IOrderService;
import com.dalhousie.MealStop.tests_support.TestsSupport;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class RecommendationServiceImplTest {

    @Mock
    private IMealService mealService;
    @Mock
    private IOrderService orderService;

    @Autowired
    @InjectMocks
    private RecommendationServiceImpl recommendationService;
    private Orders order1;
    private List<Orders> ordersList;
    private Meal meal1;
    private List<Meal> mealList;
    private Restaurant restaurant1;
    private List<Restaurant> restaurantList;
    private Map<String, Integer> tagsCountMap;
    private List<String> favTags;
    private List<Long> restuarantIdentifiers;
    private TestsSupport testsSupport = new TestsSupport();

    @BeforeEach
    void setUp() {
        mealList = new ArrayList<>();
        meal1 = testsSupport.createDummyMeal();
        meal1.setId(1L);
        restaurant1 = testsSupport.createDummyRestaurant();
        restaurant1.setId(1L);
        mealList.add(meal1);
        order1 = testsSupport.createDummyOrder(1);
        ordersList = new ArrayList<>();
        ordersList.add(order1);
        restaurantList = new ArrayList<>();
        restaurantList.add(restaurant1);
        meal1.setRestaurant(restaurant1);
        tagsCountMap = new HashMap<>();
        tagsCountMap.put("fat", 1);
        tagsCountMap.put("protein", 1);
        favTags = new ArrayList<>();
        favTags.add("fat");
        favTags.add("protein");
        restuarantIdentifiers = new ArrayList<>();
        restuarantIdentifiers.add(1L);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllRecommendedMeals() {
        when(orderService.getOrdersByCustomerID(1L)).thenReturn(ordersList);
        when(mealService.getMealByMealId(1L)).thenReturn(meal1);

        assertEquals(1, recommendationService.getAllRecommendedMeals(1L, restaurantList).size());
    }

    @Test
    void getMealTagsCount(){
        assertEquals(2, recommendationService.getMealTagsCount(mealList).size());
    }

    @Test
    void getOrderedTagsList(){
        assertEquals(2, recommendationService.getOrderedTagsList(tagsCountMap).size());
    }

    @Test
    void getMeals(){
        assertEquals(1, recommendationService.getMeals(favTags, mealList, restuarantIdentifiers).size());
    }
}