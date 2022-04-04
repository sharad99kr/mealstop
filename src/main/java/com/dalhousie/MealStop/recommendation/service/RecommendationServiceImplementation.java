package com.dalhousie.MealStop.recommendation.service;

import com.dalhousie.MealStop.meal.model.Meal;
import com.dalhousie.MealStop.meal.service.IMealService;
import com.dalhousie.MealStop.restaurant.model.Restaurant;
import com.dalhousie.MealStop.orders.model.Orders;
import com.dalhousie.MealStop.orders.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.dalhousie.MealStop.common.RecommendationConstants.NUMBER_OF_RECOMMENDED_MEALS;

@Service
public class RecommendationServiceImplementation implements IRecommendationService {

    @Autowired
    private IMealService mealService;

    @Autowired
    private IOrderService orderService;

    /**
     * Gets the recommended meals for the user based on past orders
     *
     * @param userId the logged in customer user id
     * @param availableRestaurants the restaurants available based on date range selection
     * @return list of top 5 meals to be recommended
     */
    @Override
    public List<Meal> getAllRecommendedMeals(long userId, List<Restaurant> availableRestaurants)
    {
        List<Meal> mealList = new ArrayList<>();
        List<Orders> orderList = orderService.getOrdersByCustomerID(userId);

        if(orderList.size() == 0)
            return mealList;

        List<Long> restaurantIdentifiers =
                availableRestaurants.stream()
                        .map(Restaurant::getId)
                        .collect(Collectors.toList());

        for(Orders order : orderList)
            mealList.add(mealService.getMealByMealId(order.getMealId()));

        if(mealList.size() <= NUMBER_OF_RECOMMENDED_MEALS)
            return mealList.stream()
                    .distinct()
                    .collect(Collectors.toList());


        Map<String, Integer> tagsCountMap= getMealTagsCount(mealList);
        List<String> favTags = getOrderedTagsList(tagsCountMap);
        return getMeals(favTags, mealList, restaurantIdentifiers);
    }

    /**
     * Gets a map of meal tag count. For e.g. [(fat:3), (protein:5)]
     *
     * @param mealList list of all the meals ordered by customer
     * @return map having count of each meal tag
     */
    public Map<String, Integer> getMealTagsCount(List<Meal> mealList){
        Map<String, Integer> tagsCountMap = new HashMap<>();
        for(Meal meal : mealList)
        {
            String[] tags = meal.getTags().toLowerCase().split(",");
            for(String tag : tags)
            {
                String trimTag = tag.trim();
                if (!tagsCountMap.containsKey(trimTag)) {
                    tagsCountMap.put(trimTag, 1);
                } else {
                    Integer count = tagsCountMap.get(trimTag);
                    tagsCountMap.put(trimTag, count + 1);
                }
            }
        }
        return tagsCountMap;
    }

    /**
     * Gets the ordered list of tags liked by customer
     *
     * @param tagsCountMap map having count of each meal tag
     * @return ordered list of tags by count
     */
    public List<String> getOrderedTagsList(Map<String, Integer> tagsCountMap){
        List<Map.Entry<String, Integer> > list =
                new LinkedList<>(tagsCountMap.entrySet());

        Collections.sort(list, Comparator.comparing(Map.Entry::getValue));

        List<String> favTags = new ArrayList<>();
        for (Map.Entry<String, Integer> kv : list) {
            favTags.add(kv.getKey());
        }

        Collections.reverse(favTags);
        return favTags;
    }

    /**
     * Gets top 5 recommended meals for the user
     *
     * @param favTags ordered list of tags by count
     * @param mealList list of meals ordered by customer
     * @param restaurantIdentifiers list of restaurants available to place order
     * @return list of top 5 meals to be recommended
     */
    public List<Meal> getMeals(List<String> favTags, List<Meal> mealList, List<Long> restaurantIdentifiers){
        List<Meal> recommendedMealList = new ArrayList<>();
        for(String tag : favTags)
        {
            if(recommendedMealList.size() == NUMBER_OF_RECOMMENDED_MEALS)
                break;

            for(Meal meal : mealList)
            {
                List<String> tags = new ArrayList<>();
                for(String word : meal.getTags().toLowerCase().split(","))
                    tags.add(word.trim());

                if(tags.contains(tag) && restaurantIdentifiers.contains(meal.getRestaurant().getId()))
                {
                    recommendedMealList.add(meal);
                }

                if(recommendedMealList.size() == NUMBER_OF_RECOMMENDED_MEALS)
                    break;
            }
        }

        return recommendedMealList.stream()
                .distinct()
                .collect(Collectors.toList());
    }
}
