package com.dalhousie.MealStop.cart.service;

import com.dalhousie.MealStop.meal.model.Meal;
import com.dalhousie.MealStop.cart.model.CustomerCart;

public interface ICustomerCartService
{
    public CustomerCart getCustomerCart();
    public void addMealsToCustomerCart(Meal meal);
    public void removeMealsFromCustomerCart(Meal meal);
    public void clearCustomerCart();
}
