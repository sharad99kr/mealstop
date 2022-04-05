package com.dalhousie.MealStop.meal.model;

import com.dalhousie.MealStop.restaurant.model.Restaurant;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "meal")
public class Meal implements IMeal {

    public Meal(){
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty(message = "{Null.Meal.Name}")
    @Column(name = "mealname")
    private String mealName;

    @NotEmpty(message = "{Null.Meal.Calories}")
    @Column(name = "calories")
    private String calories;

    @NotEmpty(message = "{Null.Meal.Tags}")
    @Column(name = "tags")
    private String tags;

    @NotEmpty(message = "{Null.Meal.CuisineType}")
    @Column(name ="cuisinetype")
    private String cuisineType;

    @NotNull(message = "{Null.Meal.Price}")
    @Min(value = 1, message = "{Min.Meal.Price}")
    @Max(value = 10, message = "{Max.Meal.Price}")
    @Column(name= "price")
    private long price;

    @ManyToOne
    @JoinColumn(name = "restaurantid", referencedColumnName = "restaurantid", nullable = false)
    private Restaurant restaurant;

    @Override
    public long getId()
    {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public Restaurant getRestaurant()
    {
        return restaurant;
    }

    @Override
    public void setRestaurant(Restaurant restaurant)
    {
        this.restaurant = restaurant;
    }

    @Override
    public String getMealName()
    {
        return mealName;
    }

    @Override
    public void setMealName(String mealName)
    {
        this.mealName = mealName;
    }

    @Override
    public String getCalories()
    {
        return this.calories;
    }

    @Override
    public void setCalories(String calories)
    {
        this.calories=calories;
    }

    @Override
    public String getTags()
    {
        return tags;
    }

    @Override
    public void setTags(String tags)
    {
        this.tags = tags;
    }

    @Override
    public String getCuisineType()
    {
        return cuisineType;
    }

    @Override
    public void setCuisineType(String cuisineType)
    {
        this.cuisineType=cuisineType;
    }

    @Override
    public long getPrice()
    {
        return this.price;
    }

    @Override
    public void setPrice(long price)
    {
        this.price=price;
    }
}