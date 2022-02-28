package com.dalhousie.MealStop.Meal.controller;

import com.dalhousie.MealStop.Meal.model.Meal;
import com.dalhousie.MealStop.Meal.service.MealService;
import com.dalhousie.MealStop.Restaurant.model.Restaurant;
import com.dalhousie.MealStop.Restaurant.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MealController {

    @Autowired
    private MealService mealService;

    @PostMapping("/add_meal")
    public String addMeal(@RequestBody Meal meal, Model model)
    {
        mealService.addMeal(meal);
        model.addAttribute("meal", meal);
        return "meal/add_meal";
    }

    @PutMapping("/update_meal/{id}")
    public String updateMeal(@PathVariable("id") long id, @RequestBody Meal meal, Model model)
    {
        mealService.updateMeal(id, meal);
        model.addAttribute("updatedmeal", meal);
        return "meal/update_meal";
    }

    @GetMapping("/get_meal")
    public String getAllMeals(Model model)
    {
        List<Meal> mealList = mealService.getAllMeals();
        model.addAttribute("meal_list", mealList);
        return "meal/get_meal";
    }
}
