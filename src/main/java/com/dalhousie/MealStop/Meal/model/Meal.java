package com.dalhousie.MealStop.Meal.model;

import com.dalhousie.MealStop.Restaurant.model.Restaurant;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "meal")
public class Meal implements IMeal {

    public Meal(){
        // Add here init stuff if needed
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "mealname")
    private String mealName;

    @Column(name = "calories")
    private String calories;

    @Column(name = "tags")
    private String tags;

    @Column(name ="cuisinetype")
    private String cuisineType;

    @Column(name= "price")
    private long price;

    @ManyToOne
    @JoinColumn(name = "restaurantid", referencedColumnName = "restaurantid", nullable = false)
    private Restaurant restaurant;

    public Meal(String mealName, String calories, String tags, String cuisineType, long price)
    {
        this.mealName = mealName;
        this.calories = calories;
        this.tags=tags;
        this.cuisineType=cuisineType;
        this.price=price;
    }

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

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Meal [id=" + id);
        sb.append(", mealName=" + mealName);
        sb.append(", calories=" + calories);
        sb.append(", tags=" + tags);
        sb.append(", cuisineType=" + cuisineType);
        sb.append(", price=" + price + "]");
        return sb.toString();
    }
}