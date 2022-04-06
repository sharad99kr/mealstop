package com.dalhousie.MealStop.meal.service;

import com.dalhousie.MealStop.meal.model.Meal;
import com.dalhousie.MealStop.meal.repository.MealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MealServiceImpl implements IMealService {
    @Autowired
    private MealRepository mealRepository;

    @Override
    public void addMeal(Meal meal)
    {
            mealRepository.save(meal);
    }


    @Override
    public List<Meal> getAllMeals()
    {
        List<Meal> mealList = mealRepository.findAll();
        return mealList;
    }

    @Override
    public List<Meal> getAllMealsByRestaurantId(long restaurantId)
    {
        List<Meal> mealList = mealRepository.findByRestaurantId(restaurantId);
        return mealList;
    }

    @Override
    public Meal getMealByMealId(long mealId)
    {
        Optional<Meal> meal = mealRepository.findById(mealId);
        if(meal.isPresent())
            return meal.get();

        return null;
    }

    @Override
    public Meal updateMeal(long id, Meal updatedMeal) {
        Optional<Meal> mealData = mealRepository.findById(id);
        if(mealData.isPresent())
        {
            Meal meal = mealData.get();
            meal.setMealName(updatedMeal.getMealName());
            meal.setCalories(updatedMeal.getCalories());
            meal.setCuisineType(updatedMeal.getCuisineType());
            meal.setPrice(updatedMeal.getPrice());
            meal.setTags(updatedMeal.getTags());
            mealRepository.save(meal);
            return meal;
        }

        return null;
    }
}
