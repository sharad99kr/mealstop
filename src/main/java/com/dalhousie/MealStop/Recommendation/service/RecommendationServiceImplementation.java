package com.dalhousie.MealStop.Recommendation.service;

import com.dalhousie.MealStop.Meal.model.Meal;
import com.dalhousie.MealStop.Meal.repository.MealRepository;
import com.dalhousie.MealStop.Recommendation.service.IRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

import static com.dalhousie.MealStop.Recommendation.constants.Constants.NUMBER_OF_RECOMMENDED_MEALS;

@Repository
public class RecommendationServiceImplementation implements IRecommendationService {

    @Autowired
    private MealRepository mealRepository;

    @Override
    public List<Meal> getAllRecommendedMeals(long userId)
    {
        //Get orders of a user
        //Get meal list from orders
        List<Meal> mealList = mealRepository.findAll();
        List<Meal> recommendedMealList = new ArrayList<>();

        if(mealList.size() <= NUMBER_OF_RECOMMENDED_MEALS)
            return mealList;

        if(mealList.size() == 0)
            return recommendedMealList;

        Map<String, Integer> tagsCountMap= new HashMap<>();
        for(Meal meal : mealList)
        {
            List<String> tags = Arrays.asList(meal.getTags());

            for(String tag : tags)
            {
                if (!tagsCountMap.containsKey(tag)) {
                        tagsCountMap.put(tag, 1);
                } else {
                    Integer count = tagsCountMap.get(tag);
                    tagsCountMap.put(tag, count + 1);
                }
            }
        }

        List<Map.Entry<String, Integer> > list =
                new LinkedList<>(tagsCountMap.entrySet());

        // Sort the list
        Collections.sort(list, Comparator.comparing(Map.Entry::getValue));
        Collections.sort(list, Collections.reverseOrder());

        // put data from sorted list to hashmap
        HashMap<String, Integer> sortedTagsCountMap = new LinkedHashMap<>();
        List<String> favTags = new ArrayList<>();
        for (Map.Entry<String, Integer> kv : list) {
            sortedTagsCountMap.put(kv.getKey(), kv.getValue());
            favTags.add(kv.getKey());
        }

        for(String tag : favTags)
        {
            if(recommendedMealList.size() == NUMBER_OF_RECOMMENDED_MEALS)
                break;

            for(Meal meal : mealList)
            {
                List<String> tags = Arrays.asList(meal.getTags());
                if(tags.contains(tag) && !recommendedMealList.contains(meal))
                {
                    recommendedMealList.add(meal);
                }

                if(recommendedMealList.size() == NUMBER_OF_RECOMMENDED_MEALS)
                    break;
            }
        }

        return recommendedMealList;
    }
}
