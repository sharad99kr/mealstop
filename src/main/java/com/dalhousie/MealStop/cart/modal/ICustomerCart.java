package com.dalhousie.MealStop.cart.modal;

import com.dalhousie.MealStop.meal.model.Meal;

import java.util.ArrayList;

public interface ICustomerCart
{
    public ArrayList<Meal> getCartItems();
    public void setCartItems(ArrayList<Meal> cartItems);
}
