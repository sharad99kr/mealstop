package com.dalhousie.MealStop.cart.service;

import com.dalhousie.MealStop.Meal.model.Meal;
import com.dalhousie.MealStop.cart.modal.CustomerCart;
import com.dalhousie.MealStop.customer.modal.ICustomer;

import java.util.HashMap;

public interface ICustomerCartService
{
    public CustomerCart getCustomerCart();
    public void addMealsToCustomerCart(Meal meal);
    public void removeMealsFromCustomerCart(Meal meal);
    public void clearCustomerCart();
}
