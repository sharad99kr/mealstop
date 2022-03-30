package com.dalhousie.MealStop.restaurant.repository;

import com.dalhousie.MealStop.restaurant.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
