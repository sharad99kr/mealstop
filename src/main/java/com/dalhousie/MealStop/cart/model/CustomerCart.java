package com.dalhousie.MealStop.cart.model;

import com.dalhousie.MealStop.meal.model.Meal;
import java.util.ArrayList;

public class CustomerCart implements ICustomerCart
{
    private ArrayList<Meal> cartItems;

    public CustomerCart()
    {
        cartItems = new ArrayList<Meal>();
    }

    @Override
    public ArrayList<Meal> getCartItems()
    {
        return this.cartItems;
    }

    @Override
    public void setCartItems(ArrayList<Meal> cartItems)
    {
        this.cartItems = cartItems;
    }
}
