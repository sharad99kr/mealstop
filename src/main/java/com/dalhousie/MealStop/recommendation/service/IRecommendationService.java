package com.dalhousie.MealStop.recommendation.service;

import com.dalhousie.MealStop.meal.model.Meal;
import com.dalhousie.MealStop.restaurant.model.Restaurant;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IRecommendationService {
    List<Meal> getAllRecommendedMeals(long userId, List<Restaurant> availableRestaurants);
}
