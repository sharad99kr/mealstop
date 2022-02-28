package com.dalhousie.MealStop.Restaurant.repository;

import com.dalhousie.MealStop.Restaurant.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
