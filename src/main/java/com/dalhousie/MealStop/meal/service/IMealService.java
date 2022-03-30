package com.dalhousie.MealStop.meal.service;

import com.dalhousie.MealStop.meal.model.Meal;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IMealService {
    public abstract void addMeal(Meal meal);

    public abstract List<Meal> getAllMeals();

    public abstract Meal updateMeal(long id, Meal meal);

    public abstract List<Meal> getAllMealsByRestaurantId(long restaurantId);

    public abstract Meal getMealByMealId(long mealId);

}