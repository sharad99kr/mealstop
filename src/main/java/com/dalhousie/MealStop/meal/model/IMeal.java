package com.dalhousie.MealStop.meal.model;

import com.dalhousie.MealStop.restaurant.model.Restaurant;

public interface IMeal {

    public long getId();

    public void setId(long id);

    public String getMealName();

    public void setMealName(String mealName);

    public String getCalories();

    public void setCalories(String calories);

    public String  getTags();

    public void setTags(String tags);

    public String getCuisineType();

    public void setCuisineType(String cuisineType);

    public long getPrice();

    public void setPrice(long price);

    public Restaurant getRestaurant();

    public void setRestaurant(Restaurant restaurant);
}
