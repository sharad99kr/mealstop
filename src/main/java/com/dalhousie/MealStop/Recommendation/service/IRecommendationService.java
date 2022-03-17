package com.dalhousie.MealStop.Recommendation.service;

import com.dalhousie.MealStop.Meal.model.Meal;
import com.dalhousie.MealStop.Restaurant.model.Restaurant;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IRecommendationService {
    public abstract List<Meal> getAllRecommendedMeals(long userId, List<Restaurant> availableRestaurants);
}
