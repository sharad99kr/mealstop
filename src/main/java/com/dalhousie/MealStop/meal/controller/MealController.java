package com.dalhousie.MealStop.meal.controller;

import com.dalhousie.MealStop.meal.model.Meal;
import com.dalhousie.MealStop.meal.service.IMealService;
import com.dalhousie.MealStop.restaurant.model.Restaurant;
import com.dalhousie.MealStop.restaurant.service.IRestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class MealController {

    @Autowired
    private IMealService mealService;

    @Autowired
    private IRestaurantService restaurantService;

    @GetMapping("/restaurant/add_meal_form/{id}")
    public String addMealForm(@PathVariable("id") long id, Model model, Meal meal)
    {
        model.addAttribute("restaurant", id);
        return "meal/add_meal";
    }

    @PostMapping("/restaurant/add_meal/{id}")
    public String addMeal(@Valid Meal meal, BindingResult result, @PathVariable("id") long id, Model model)
    {
        if (result.hasErrors()) {
            model.addAttribute("restaurant", id);
            return "meal/add_meal";
        }
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        meal.setRestaurant(restaurant);
        mealService.addMeal(meal);
        return "redirect:/restaurant/get_meal/" + id;
    }

    @GetMapping("/restaurant/editMeal/{id}")
    public String edit(@PathVariable("id") long id, Model model)
    {
        Meal meal = mealService.getMealByMealId(id);
        model.addAttribute("restaurant", meal.getRestaurant().getId());
        model.addAttribute("meal", meal);
        return "meal/update_meal";
    }

    @PostMapping("/restaurant/update_meal/{id}")
    public String updateMeal(@Valid Meal meal, BindingResult result, @PathVariable("id") long id, Model model)
    {
        if (result.hasErrors()) {
            Meal oldMeal = mealService.getMealByMealId(id);
            model.addAttribute("restaurant", oldMeal.getRestaurant().getId());
            model.addAttribute("meal", meal);
            return "meal/update_meal";
        }
        Meal updatedMeal = mealService.updateMeal(id, meal);
        return "redirect:/restaurant/get_meal/" + updatedMeal.getRestaurant().getId();
    }

    @GetMapping("/restaurant/get_meal/{id}")
    public String getAllMeals(Model model,  @PathVariable("id") long id)
    {
        List<Meal> mealList = mealService.getAllMealsByRestaurantId(id);
        model.addAttribute("meal_list", mealList);
        model.addAttribute("restaurant", id);
        return "meal/get_meal";
    }

    @GetMapping("/customer/get_meals/{id}")
    public String getRestaurantMeals(Model model,  @PathVariable("id") long id)
    {
        List<Meal> mealList = mealService.getAllMealsByRestaurantId(id);
        model.addAttribute("meal_list", mealList);
        model.addAttribute("restaurant", id);
        return "customer/meals";
    }
}
