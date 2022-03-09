package com.dalhousie.MealStop.Meal.service;

import com.dalhousie.MealStop.Meal.model.Meal;
import com.dalhousie.MealStop.Meal.repository.MealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MealServiceImplementation implements IMealService {
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
    public void updateMeal(long id, Meal meal) {
        Optional<Meal> mealData = mealRepository.findById(id);
        if(mealData.isPresent())
        {
            Meal _meal = mealData.get();
            _meal.setMealName(meal.getMealName());
            _meal.setCalories(meal.getCalories());
            _meal.setCuisineType(meal.getCuisineType());
            _meal.setPrice(meal.getPrice());
            _meal.setTags(meal.getTags());
            mealRepository.save(_meal);
        }
    }
}
