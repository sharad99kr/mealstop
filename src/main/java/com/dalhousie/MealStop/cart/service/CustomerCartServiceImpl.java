package com.dalhousie.MealStop.cart.service;

import com.dalhousie.MealStop.meal.model.Meal;
import com.dalhousie.MealStop.cart.modal.CustomerCart;
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

    private HashMap<Long, CustomerCart> customersCartMap;

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
        System.err.println("adding meals to customer cart"+cartItems);
        if(!cartItems.contains(meal))
        {
            cartItems.add(meal);
        }
    }

    @Override
    public void removeMealsFromCustomerCart(Meal meal)
    {
        ArrayList<Meal> cartItems = getLoggedInCustomerCartMeals();
        if(cartItems.contains(meal))
        {
            cartItems.remove(meal);
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
