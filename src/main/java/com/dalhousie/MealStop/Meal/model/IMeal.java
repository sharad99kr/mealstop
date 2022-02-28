package com.dalhousie.MealStop.Meal.model;

public interface IMeal {

    public long getId();

    public long getRestaurantId();

    public void setRestaurantId(long id);

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
}
