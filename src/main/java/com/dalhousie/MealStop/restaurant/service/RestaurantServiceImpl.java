package com.dalhousie.MealStop.restaurant.service;

import com.dalhousie.MealStop.common.RecommendationConstants;
import com.dalhousie.MealStop.meal.model.Meal;
import com.dalhousie.MealStop.recommendation.service.IRecommendationService;
import com.dalhousie.MealStop.restaurant.model.Restaurant;
import com.dalhousie.MealStop.restaurant.repository.RestaurantRepository;
import com.dalhousie.MealStop.customer.service.ICustomerService;
import com.dalhousie.MealStop.review.model.CustomerReview;
import com.dalhousie.MealStop.review.service.ICustomerReviewService;
import com.dalhousie.MealStop.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class RestaurantServiceImpl implements IRestaurantService {
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private ICustomerReviewService customerReviewService;

    @Autowired
    private IRecommendationService recommendationService;

    @Autowired
    private ICustomerService customerService;

    @Override
    public void addRestaurant(Restaurant restaurant)
    {
        restaurant.setUserId(getRestaurantUserDetailsFromSession().getUser_id());
        restaurantRepository.save(restaurant);
    }

    @Override
    public List<Restaurant> getAllRestaurantByUserId()
    {
        List<Restaurant> restaurantList = restaurantRepository.findAll();
        List<Restaurant> filteredList = new ArrayList<>();

        for(Restaurant restaurant : restaurantList) {
            if(restaurant.getUserId() == getRestaurantUserDetailsFromSession().getUser_id()) {
                filteredList.add(restaurant);
            }
        }

        if(filteredList.size() == 0) {
            return filteredList;
        }

        for(Restaurant restaurant : filteredList) {
            restaurant.setAvgReviewScore(getAverageReviewScore(restaurant));
        }

        return filteredList;
    }

    public String getAverageReviewScore(Restaurant restaurant)
    {
        List<CustomerReview>  restaurantReviews= customerReviewService.getReviewsOfRestaurant(restaurant);
        double reviewScore = 0;

        if(restaurantReviews.size() == 0) {
            return RecommendationConstants.NO_REVIEW_MESSAGE;
        }

        for(CustomerReview review : restaurantReviews) {
            reviewScore += review.getReviewScore();
        }

        DecimalFormat decimalFormat = new DecimalFormat(RecommendationConstants.DECIMAL_PATTERN);
        double avgScore = (reviewScore/restaurantReviews.size());
        return String.valueOf(decimalFormat.format(avgScore));
    }

    @Override
    public List<String> getRestaurantReviews(long id)
    {
        List<String> reviewMessages = new ArrayList<>();
        Restaurant restaurant = getRestaurantById(id);
        List<CustomerReview> reviews= customerReviewService.getReviewsOfRestaurant(restaurant);

        if(reviews.size() == 0) {
            return reviewMessages;
        }

        for(CustomerReview review : reviews) {
            reviewMessages.add(review.getReviewMessage());
        }

        return reviewMessages;
    }

    @Override
    public Restaurant updateRestaurant(Restaurant updatedRestaurant, long id)
    {
        Optional<Restaurant> restaurantData = restaurantRepository.findById(id);
        if(restaurantData.isPresent())
        {
            Restaurant restaurant = restaurantData.get();
            restaurant.setRestaurantName(updatedRestaurant.getRestaurantName());
            restaurant.setAddress(updatedRestaurant.getAddress());
            restaurant.setEmail(updatedRestaurant.getEmail());
            restaurant.setPhoneNumber(updatedRestaurant.getPhoneNumber());
            restaurant.setAvailability(updatedRestaurant.getAvailability());
            restaurantRepository.save(restaurant);
            return restaurant;
        }
        return null;
    }

    @Override
    public List<Restaurant> getAvailableRestaurants(Date startDate, Date endDate){
        List<Restaurant> allRestaurants = restaurantRepository.findAll();
        List<Restaurant> availableRestaurants = new ArrayList<>();

        if(allRestaurants.size() > 0)
        {
            if(startDate == null || endDate == null)
            {
                log.error("Selected dates are not in correct range");
            }
            else
            {
                DateFormat formatter = new SimpleDateFormat(RecommendationConstants.DATE_PATTERN, Locale.ENGLISH);
                List<String> daysSelected = new ArrayList<>();

                Calendar cal = Calendar.getInstance();
                while(startDate.compareTo(endDate) <= 0)
                {
                    String weekday = formatter.format(startDate);
                    if(!daysSelected.contains(weekday)) {
                        daysSelected.add(weekday);
                    }
                    cal.setTime(startDate);
                    cal.add(Calendar.DATE, 1);
                    startDate = cal.getTime();
                }

                allRestaurants.forEach((restaurant) -> daysSelected.forEach((weekDay) ->{
                    if(restaurant.getAvailability().toLowerCase().contains(weekDay.toLowerCase())) {
                        if(!availableRestaurants.contains(restaurant)) {
                            availableRestaurants.add(restaurant);
                        }
                    }}));
                return availableRestaurants;
            }
        }

        return availableRestaurants;
    }

    @Override
    public Restaurant getRestaurantById(Long Id) {
        Restaurant restaurant = restaurantRepository.findById(Id).orElse(null);
        return restaurant;
    }

    @Override
    public List<Meal> getRecommendedMealForCustomer(List<Restaurant> availableRestaurants)
    {
        return recommendationService.getAllRecommendedMeals(customerService.getLoggedInCustomerId(), availableRestaurants);
    }

    @Override
    public User getRestaurantUserDetailsFromSession()
    {
        try
        {
            User user = (User)SecurityContextHolder.getContext().getAuthentication().getDetails();
            return user;
        }
        catch(Exception e)
        {
            log.error(e.getMessage());
        }
        return null;
    }
}
