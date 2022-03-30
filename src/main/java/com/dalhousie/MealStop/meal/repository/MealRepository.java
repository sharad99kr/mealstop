package com.dalhousie.MealStop.meal.repository;

import com.dalhousie.MealStop.meal.model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MealRepository extends JpaRepository<Meal, Long> {
    public List<Meal> findByRestaurantId(long restaurantId);
}
