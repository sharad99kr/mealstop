package com.dalhousie.MealStop.cart.service;

import com.dalhousie.MealStop.meal.model.Meal;
import com.dalhousie.MealStop.cart.model.CustomerCart;
import com.dalhousie.MealStop.customer.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class CustomerCartServiceImpl implements ICustomerCartService
{
    @Autowired
    private ICustomerService customerService;

    private final HashMap<Long, CustomerCart> customersCartMap;

    public CustomerCartServiceImpl()
    {
        customersCartMap = new HashMap<Long, CustomerCart>();
    }

    @Override
    public CustomerCart getCustomerCart()
    {
        Long customerId = customerService.getLoggedInCustomerId();

        if(!isCustomerCartExists(customerId))
        {
            CustomerCart CustomerCart = new CustomerCart();
            customersCartMap.put(customerId, CustomerCart);
        }
        return customersCartMap.get(customerId);
    }

    @Override
    public void addMealsToCustomerCart(Meal meal)
    {
        ArrayList<Meal> cartItems = getLoggedInCustomerCartMeals();
        if(!cartItems.contains(meal))
        {
            cartItems.add(meal);
        }
    }

    @Override
    public void removeMealsFromCustomerCart(Meal meal)
    {
        ArrayList<Meal> cartItems = getLoggedInCustomerCartMeals();
        int mealIndex = -1;

        for(int index = 0; index<cartItems.size(); index++)
        {
            Meal presentMeal = cartItems.get(index);
            if(presentMeal.getId()==meal.getId())
            {
                mealIndex = index;
            }
        }

        if(mealIndex!=-1)
        {
            cartItems.remove(mealIndex);
        }
    }

    private ArrayList<Meal> getLoggedInCustomerCartMeals()
    {
        Long customerId = customerService.getLoggedInCustomerId();
        if(!isCustomerCartExists(customerId))
        {
            CustomerCart CustomerCart = new CustomerCart();
            customersCartMap.put(customerId, CustomerCart);
        }
        return customersCartMap.get(customerId).getCartItems();
    }

    private boolean isCustomerCartExists(Long customerId)
    {
        return customersCartMap.containsKey(customerId);
    }

    public void clearCustomerCart()
    {
        CustomerCart customerCart = getCustomerCart();
        customerCart.getCartItems().clear();
    }
}
