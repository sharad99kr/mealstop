package com.dalhousie.MealStop.meal.service;

import com.dalhousie.MealStop.meal.model.Meal;
import com.dalhousie.MealStop.meal.repository.MealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MealServiceImplementation implements IMealService {
    @Autowired
    private MealRepository mealRepository;

    /**
     * Saves a new meal information related to restaurant
     *
     * @param meal meal information which is to be added
     */
    @Override
    public void addMeal(Meal meal)
    {
        if(!checkDuplicateMeal(meal))
            mealRepository.save(meal);
    }

    /**
     * Gets all the meals
     *
     * @return list of meals if any
     */
    @Override
    public List<Meal> getAllMeals()
    {
        List<Meal> mealList = mealRepository.findAll();
        return mealList;
    }

    /**
     * Gets all the meals related to a restaurant
     *
     * @param restaurantId restaurant id for which meals are needed
     * @return list of meals if any
     */
    @Override
    public List<Meal> getAllMealsByRestaurantId(long restaurantId)
    {
        List<Meal> mealList = mealRepository.findByRestaurantId(restaurantId);
        return mealList;
    }

    /**
     * Gets the meal details
     *
     * @param mealId the meal id for which information is required
     * @return meal details
     */
    @Override
    public Meal getMealByMealId(long mealId)
    {
        Optional<Meal> meal = mealRepository.findById(mealId);
        if(meal.isPresent())
            return meal.get();

        return null;
    }

    /**
     * Updates the meal information
     *
     * @param id the meal id for which update is performed
     * @param updatedMeal the updated meal information
     * @return updatedMeal
     */
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
            if(!checkDuplicateMeal(meal))
                mealRepository.save(meal);
            return meal;
        }

        return null;
    }

    /**
     * Check for the duplicate meal in the database for the restaurant
     *
     * @param meal meal to be checked for duplicate
     * @return true if duplicate is available else false
     */
    @Override
    public boolean checkDuplicateMeal(Meal meal)
    {
        List<Meal> mealList = getAllMealsByRestaurantId(meal.getRestaurant().getId());
        boolean isDuplicate = false;
        for(Meal restaurantMeal: mealList)
        {
            if(restaurantMeal.getMealName().equalsIgnoreCase(meal.getMealName()))
                isDuplicate = true;
        }

        return isDuplicate;
    }
}
