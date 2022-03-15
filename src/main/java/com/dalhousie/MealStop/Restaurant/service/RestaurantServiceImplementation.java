package com.dalhousie.MealStop.Restaurant.service;

import com.dalhousie.MealStop.Meal.service.MealService;
import com.dalhousie.MealStop.Restaurant.model.Restaurant;
import com.dalhousie.MealStop.Restaurant.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Repository
public class RestaurantServiceImplementation implements RestaurantService {
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private MealService mealService;

    @Override
    public void addRestaurant(Restaurant restaurant)
    {
        restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant getRestaurantById(long id)
    {
        Optional<Restaurant> restaurnt = restaurantRepository.findById(id);
        return restaurnt.get();
    }

    @Override
    public List<Restaurant> getAllRestaurant()
    {
        List<Restaurant> restaurantList = restaurantRepository.findAll();
        return restaurantList;
    }

    @Override
    public List<Restaurant> getAvailableRestaurants(Date startDate, Date endDate)
    {
        List<Restaurant> allRestaurants = restaurantRepository.findAll();
        List<Restaurant> availableRestaurants = new ArrayList<>();

        if(allRestaurants != null)
        {
            if(startDate == null || endDate == null)
            {
                new Exception("Please select a valid range");
            }
            else
            {
                DateFormat formatter = new SimpleDateFormat("EEEE", Locale.ENGLISH);
                String weekday = formatter.format(endDate);
                List<String> daysSelected = new ArrayList<>();
                daysSelected.add(weekday);

                Calendar cal = Calendar.getInstance();
                cal.setTime(startDate);

                while(startDate != endDate)
                {
                    cal.add(Calendar.DATE, 1);
                    endDate = cal.getTime();
                    weekday = formatter.format(endDate);
                    if(!daysSelected.contains(weekday))
                        daysSelected.add(weekday);
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

    @Override
    public void updateRestaurant(Long id, Restaurant restaurant)
    {
        Optional<Restaurant> restaurantData = restaurantRepository.findById(id);
        if(restaurantData.isPresent())
        {
            Restaurant _restaurant = restaurantData.get();
            _restaurant.setRestaurantName(restaurant.getRestaurantName());
            _restaurant.setAddress(restaurant.getAddress());
            _restaurant.setEmail(restaurant.getEmail());
            _restaurant.setAccountStatus(restaurant.getAccountStatus());
            _restaurant.setPhoneNumber(restaurant.getPhoneNumber());
            _restaurant.setAvailability(restaurant.getAvailability());
            restaurantRepository.save(_restaurant);
        }
    }
}
