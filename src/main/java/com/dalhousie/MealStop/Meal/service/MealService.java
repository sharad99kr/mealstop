package com.dalhousie.MealStop.Meal.service;

import com.dalhousie.MealStop.Meal.model.Meal;

import java.util.List;

public interface MealService {
    public abstract void addMeal(Meal meal);
    public abstract List<Meal> getAllMeals();
    public abstract void updateMeal(long id, Meal meal);
}
