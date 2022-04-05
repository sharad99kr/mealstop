package com.dalhousie.MealStop.meal.model;

import com.dalhousie.MealStop.restaurant.model.Restaurant;

public interface IMeal {

     long getId();

     void setId(long id);

     String getMealName();

     void setMealName(String mealName);

     String getCalories();

     void setCalories(String calories);

     String  getTags();

     void setTags(String tags);

     String getCuisineType();

     void setCuisineType(String cuisineType);

     long getPrice();

     void setPrice(long price);

     Restaurant getRestaurant();

     void setRestaurant(Restaurant restaurant);
}
