package com.dalhousie.MealStop.meal.builder;

import com.dalhousie.MealStop.meal.model.Meal;

public class MealBuilder {
    private final Meal meal;

    public MealBuilder()
    {
        meal = new Meal();
    }

    public void setMealName(String mealName) {
        this.meal.setMealName(mealName);
    }

    public void setCalories(String calories) {
        this.meal.setCalories(calories);
    }

    public void setTags(String tags) {
        this.meal.setTags(tags);
    }

    public void setCuisineType(String cuisineType) {
        this.meal.setCuisineType(cuisineType);
    }

    public void setPrice(long price) {
        this.meal.setPrice(price);
    }

    public Meal createMeal() {
        return meal;
    }
}