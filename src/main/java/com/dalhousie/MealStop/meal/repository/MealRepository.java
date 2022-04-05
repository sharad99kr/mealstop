package com.dalhousie.MealStop.meal.repository;

import com.dalhousie.MealStop.meal.model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
     List<Meal> findByRestaurantId(long restaurantId);
}
