package com.dalhousie.MealStop.Recommendation.service;

import com.dalhousie.MealStop.Meal.model.Meal;

import java.util.List;

public interface RecommendationService {
    public abstract List<Meal> getAllRecommendedMeals(long userId);
}
