package com.dalhousie.MealStop.cart.service;

import com.dalhousie.MealStop.meal.model.Meal;
import com.dalhousie.MealStop.cart.model.CustomerCart;

public interface ICustomerCartService
{
     CustomerCart getCustomerCart();
     void addMealsToCustomerCart(Meal meal);
     void removeMealsFromCustomerCart(Meal meal);
     void clearCustomerCart();
}
