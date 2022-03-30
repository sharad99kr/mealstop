package com.dalhousie.MealStop.meal.service;

import com.dalhousie.MealStop.meal.model.Meal;
import com.dalhousie.MealStop.meal.repository.MealRepository;
import com.dalhousie.MealStop.restaurant.model.Restaurant;
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
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class MealServiceImplementationTest {

    @Mock
    private MealRepository mealRepository;

    @Autowired
    @InjectMocks
    private MealServiceImplementation mealService;
    private Meal meal1;
    private Meal meal2;
    List<Meal> mealList;

    @BeforeEach
    void setUp() {
        mealList = new ArrayList<>();
        meal1 = new Meal("ThaiMeal", "120","fat, protein", "Thai", 100);
        meal2 = new Meal("ChineseMeal", "140","fat, carbs", "Chinese", 150);
        meal1.setId(1L);
        Restaurant restaurant = new Restaurant();
        restaurant.setId(2L);
        meal1.setRestaurant(restaurant);
        meal2.setRestaurant(restaurant);
        mealList.add(meal1);
        mealList.add(meal2);
    }

    @AfterEach
    void tearDown() {
        meal1 = meal2 = null;
        mealList = null;
    }

    @Test
    void addMeal() {
        when(mealRepository.save(any())).thenReturn(meal1);
        mealService.addMeal(meal1);
        verify(mealRepository,times(1)).save(any());
    }

    @Test
    void getAllMeals() {
        mealRepository.save(meal1);
        when(mealRepository.findAll()).thenReturn(mealList);
        List<Meal> mealList1 = mealService.getAllMeals();
        assertEquals(mealList1, mealList);
        verify(mealRepository, times(1)).save(meal1);
        verify(mealRepository, times(1)).findAll();
    }

    @Test
    void getAllMealsByRestaurantId() {
        when(mealRepository.findByRestaurantId(2L)).thenReturn(mealList);
        assertThat(mealService.getAllMealsByRestaurantId(meal1.getRestaurant().getId())).isEqualTo(mealList);
    }

    @Test
    void getMealByMealId() {
        when(mealRepository.findById(1L)).thenReturn(Optional.ofNullable(meal1));
        assertThat(mealService.getMealByMealId(meal1.getId())).isEqualTo(meal1);
    }

    @Test
    void updateMeal() {
        when(mealRepository.findById(1L)).thenReturn(Optional.ofNullable(meal1));
        mealService.updateMeal(meal1.getId(), meal1);
        verify(mealRepository,times(1)).save(any());
    }
}