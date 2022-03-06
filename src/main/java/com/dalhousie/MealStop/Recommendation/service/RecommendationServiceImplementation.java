package com.dalhousie.MealStop.Recommendation.service;

import com.dalhousie.MealStop.Meal.model.Meal;
import com.dalhousie.MealStop.Meal.repository.MealRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class RecommendationServiceImplementation implements RecommendationService{

    @Autowired
    private MealRepository mealRepository;

    private static final int recommendedMeals=5;

    @Override
    public List<Meal> getAllRecommendedMeals(long userId)
    {
        //Get orders of a user
        //Get meal list from orders
        List<Meal> mealList = mealRepository.findAll();
        List<Meal> recommendedMealList = new ArrayList<>();

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

        int counter = recommendedMeals > sortedTagsCountMap.size() ? recommendedMeals : sortedTagsCountMap.size();
        for(String tag : favTags)
        {
            if(recommendedMealList.size() == recommendedMeals)
                break;

            for(Meal meal : mealList)
            {
                List<String> tags = Arrays.asList(meal.getTags());
                if(tags.contains(tag))
                    recommendedMealList.add(meal);

                if(recommendedMealList.size() == recommendedMeals)
                    break;
            }
        }

        return recommendedMealList;
    }
}
