package com.dalhousie.MealStop.Meal.service;

import com.dalhousie.MealStop.Meal.repository.MealRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)

class MealServiceImplementationTest {

    @InjectMocks
    private MealServiceImplementation mealService;


    @Mock
    private MealRepository mealRepository;


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addMeal() {
    }

    @Test
    void getAllMeals() {
        assertTrue(mealService.getAllMeals().size()>0);
    }

    @Test
    void updateMeal() {
    }
}