package com.dalhousie.MealStop.Meal.repository;

import com.dalhousie.MealStop.Meal.model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MealRepository extends JpaRepository<Meal, Long> {
}
