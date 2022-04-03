package com.dalhousie.MealStop.tests_support;

import com.dalhousie.MealStop.meal.builder.MealBuilder;
import com.dalhousie.MealStop.meal.model.Meal;
import com.dalhousie.MealStop.restaurant.builder.RestaurantBuilder;
import com.dalhousie.MealStop.restaurant.model.Restaurant;

public class TestsSupport {

    public Restaurant createDummyRestaurant()
    {
        RestaurantBuilder restaurantBuilder = new RestaurantBuilder();
        restaurantBuilder.setRestaurantName("Restaurant1");
        restaurantBuilder.setUserID(1L);
        restaurantBuilder.setAvailability("monday, tuesday");
        restaurantBuilder.setEmail("p@gmail.com");
        restaurantBuilder.setPhoneNumber("9029893443");
        restaurantBuilder.setAddress("911 Park Victoria");
        return restaurantBuilder.createRestaurant();
    }

    public Meal createDummyMeal(){
        MealBuilder mealBuilder = new MealBuilder();
        mealBuilder.setMealName("ThaiMeal");
        mealBuilder.setCalories("120");
        mealBuilder.setTags("fat, protein");
        mealBuilder.setCuisineType("Thai");
        mealBuilder.setPrice(3);
        return mealBuilder.createMeal();
    }
}
