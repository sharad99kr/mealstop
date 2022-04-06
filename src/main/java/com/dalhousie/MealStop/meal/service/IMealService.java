package com.dalhousie.MealStop.meal.service;

import com.dalhousie.MealStop.meal.model.Meal;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IMealService {
     void addMeal(Meal meal);

     List<Meal> getAllMeals();

     Meal updateMeal(long id, Meal meal);

     List<Meal> getAllMealsByRestaurantId(long restaurantId);

     Meal getMealByMealId(long mealId);
}