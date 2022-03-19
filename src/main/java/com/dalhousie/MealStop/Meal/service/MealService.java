package com.dalhousie.MealStop.Meal.service;

import com.dalhousie.MealStop.Meal.model.Meal;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MealService {
    public abstract void addMeal(Meal meal);
    public abstract List<Meal> getAllMeals();
    public abstract void updateMeal(long id, Meal meal);
    public Meal getMealByMealId(long mealId);
}
