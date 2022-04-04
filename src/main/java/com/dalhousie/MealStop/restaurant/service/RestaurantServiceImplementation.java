package com.dalhousie.MealStop.restaurant.service;

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
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class RestaurantServiceImplementation implements IRestaurantService {
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private ICustomerReviewService customerReviewService;

    @Autowired
    private IRecommendationService recommendationService;

    @Autowired
    private ICustomerService customerService;

    /**
     * Saves a new restaurant information related to restaurant owner
     *
     * @param restaurant restaurant information which is to be added
     */
    @Override
    public void addRestaurant(Restaurant restaurant)
    {
        restaurant.setUserId(getRestaurantUserDetailsFromSession().getUser_id());
        if(!checkDuplicateRestaurant(restaurant))
            restaurantRepository.save(restaurant);
    }

    /**
     * Gets all the restaurants related to logged in restaurant user
     *
     * @return list of restaurants if any
     */
    @Override
    public List<Restaurant> getAllRestaurantByUserId()
    {
        List<Restaurant> restaurantList = restaurantRepository.findAll();
        List<Restaurant> filteredList = new ArrayList<>();

        for(Restaurant restaurant : restaurantList)
        {
            if(restaurant.getUserId() == getRestaurantUserDetailsFromSession().getUser_id())
                filteredList.add(restaurant);
        }

        if(filteredList.size() == 0)
            return filteredList;

        for(Restaurant restaurant : filteredList)
        {
            restaurant.setAvgReviewScore(getAverageReviewScore(restaurant));
        }

        return filteredList;
    }

    /**
     * Gets the average score of reviews for a restaurant
     *
     * @param restaurant restaurant for which review score is needed
     * @return "No Reviews" if no reviews, otherwise review score average
     */
    public String getAverageReviewScore(Restaurant restaurant)
    {
        List<CustomerReview>  restaurantReviews= customerReviewService.getReviewsOfRestaurant(restaurant);

        if(restaurantReviews.size() == 0)
            return "No Reviews";

        double reviewScore = 0;

        for(CustomerReview review : restaurantReviews)
            reviewScore += review.getReviewScore();

        return String.valueOf(reviewScore/restaurantReviews.size());
    }

    /**
     * Gets the reviews for a restaurant
     *
     * @param id restaurant id for which reviews are needed
     * @return list of reviews
     */
    @Override
    public List<String> getRestaurantReviews(long id)
    {
        Restaurant restaurant = getRestaurantById(id);
        List<CustomerReview> reviews= customerReviewService.getReviewsOfRestaurant(restaurant);
        List<String> reviewMsgs = new ArrayList<>();
        if(reviews.size() == 0)
            return reviewMsgs;

        for(CustomerReview review : reviews)
        {
            reviewMsgs.add(review.getReviewMessage());
        }

        return reviewMsgs;
    }

    /**
     * Updates the restaurant information
     *
     * @param updatedRestaurant the updated restaurant information
     * @param id the restaurant id for which update is performed
     * @return updated restaurant
     */
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
            if(!checkDuplicateRestaurant(restaurant))
                restaurantRepository.save(restaurant);
            return restaurant;
        }
        return null;
    }

    /**
     * Gets the list of available restaurants based on date range selection
     *
     * @param startDate the "from" date
     * @param endDate the "to" date
     * @return List of available restaurants base on date range selection
     */
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
                DateFormat formatter = new SimpleDateFormat("EEEE", Locale.ENGLISH);
                List<String> daysSelected = new ArrayList<>();

                Calendar cal = Calendar.getInstance();
                while(startDate.compareTo(endDate) <= 0)
                {
                    String weekday = formatter.format(startDate);
                    if(!daysSelected.contains(weekday))
                        daysSelected.add(weekday);

                    cal.setTime(startDate);
                    cal.add(Calendar.DATE, 1);
                    startDate = cal.getTime();
                }


                allRestaurants.forEach((restaurant) -> daysSelected.forEach((weekDay) ->{

                    if(restaurant.getAvailability().toLowerCase().contains(weekDay.toLowerCase()))
                    {
                        if(!availableRestaurants.contains(restaurant))
                            availableRestaurants.add(restaurant);
                    }
                }));

                return availableRestaurants;
            }
        }

        return availableRestaurants;
    }

    /**
     * Gets the restaurant details
     *
     * @param Id the restaurant id for which information is required
     * @return restaurant details
     */
    @Override
    public Restaurant getRestaurantById(Long Id) {
        Restaurant restaurant = restaurantRepository.findById(Id).orElse(null);
        return restaurant;
    }

    /**
     * Gets the recommended meals for the user
     *
     * @param availableRestaurants the restaurants available based on date range selection
     * @return list of top 5 meals to be recommended
     */
    @Override
    public List<Meal> getRecommendedMealForCustomer(List<Restaurant> availableRestaurants)
    {
        return recommendationService.getAllRecommendedMeals(customerService.getLoggedInCustomerId(), availableRestaurants);
    }

    /**
     * Gets the logged in user details
     *
     * @return user details
     */
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
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Check for the duplicate restaurant in the database for the restautrant user
     *
     * @param restaurant restaurant to be checked for duplicate
     * @return true if duplicate is available else false
     */
    @Override
    public boolean checkDuplicateRestaurant(Restaurant restaurant)
    {
        List<Restaurant> restaurantList = getAllRestaurantByUserId();
        boolean isDuplicate = false;
        String resName = restaurant.getRestaurantName();
        String resAddr = restaurant.getAddress();
        for(Restaurant userRestaurant: restaurantList)
        {
            String name = userRestaurant.getRestaurantName();
            String address = userRestaurant.getAddress();
            if(name.equalsIgnoreCase(resName)
            || address.equalsIgnoreCase(resAddr))
                isDuplicate = true;
        }

        return isDuplicate;
    }
}
