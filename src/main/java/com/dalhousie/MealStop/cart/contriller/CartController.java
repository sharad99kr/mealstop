package com.dalhousie.MealStop.cart.contriller;

import com.dalhousie.MealStop.Meal.model.Meal;
import com.dalhousie.MealStop.Meal.service.IMealService;
import com.dalhousie.MealStop.cart.modal.CustomerCart;
import com.dalhousie.MealStop.cart.service.ICustomerCartService;
import com.dalhousie.MealStop.customer.modal.Customer;
import com.dalhousie.MealStop.customer.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CartController
{
    @Autowired
    ICustomerCartService cartService;

    @Autowired
    ICustomerService customerService;

    @Autowired
    IMealService mealService;

    @GetMapping("/customer/cart")
    public String getCustomerCartPage(Model model)
    {
        CustomerCart customerCart = cartService.getCustomerCart();
        model.addAttribute("cart", customerCart);

        Customer loggedInCustomer = customerService.getCustomerDetailsFromSession();
        model.addAttribute("customer", loggedInCustomer);

        System.err.println(customerCart);
        return "cart/get_carts";
    }

    @PostMapping("/customer/cart/add_item/{id}")
    public String addItemsToCart(@PathVariable("id") long mealId)
    {
        Meal meal = mealService.getMealByMealId(mealId);
        cartService.addMealsToCustomerCart(meal);
        return "redirect:/customer/cart";
    }

    @PostMapping("/customer/cart/remove_item/{id}")
    public String removeItemsFromCart(@PathVariable("id") long mealId)
    {
        Meal meal = mealService.getMealByMealId(mealId);
        cartService.removeMealsFromCustomerCart(meal);
        return "redirect:/customer/cart";
    }
}
