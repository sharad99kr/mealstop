package com.dalhousie.MealStop.tests_support;

import com.dalhousie.MealStop.customer.builder.CustomerBuilder;
import com.dalhousie.MealStop.customer.model.Customer;
import com.dalhousie.MealStop.meal.builder.MealBuilder;
import com.dalhousie.MealStop.meal.model.Meal;
import com.dalhousie.MealStop.orders.model.Orders;
import com.dalhousie.MealStop.restaurant.builder.RestaurantBuilder;
import com.dalhousie.MealStop.restaurant.model.Restaurant;

public class TestsSupport {

    public Restaurant createDummyRestaurant()
    {
        RestaurantBuilder restaurantBuilder = new RestaurantBuilder();
        restaurantBuilder.setRestaurantName("Restaurant1");
        restaurantBuilder.setUserID(1L);
        restaurantBuilder.setAvailability("monday, tuesday");
        restaurantBuilder.setEmail("p@gmail.com");
        restaurantBuilder.setPhoneNumber("9029893443");
        restaurantBuilder.setAddress("911 Park Victoria");
        return restaurantBuilder.createRestaurant();
    }

    public Meal createDummyMeal(){
        MealBuilder mealBuilder = new MealBuilder();
        mealBuilder.setMealName("ThaiMeal");
        mealBuilder.setCalories("120");
        mealBuilder.setTags("fat, protein");
        mealBuilder.setCuisineType("Thai");
        mealBuilder.setPrice(3);
        return mealBuilder.createMeal();
    }

    public Customer createDummyCustomer() {
        CustomerBuilder customerBuilder = new CustomerBuilder();
        customerBuilder.setId(1L);
        customerBuilder.setFirstName("Shathish");
        customerBuilder.setLastName("Annamalai");
        customerBuilder.setEmail("abc@gmail.com");
        customerBuilder.setAddress("Halifax, NS, Canada");
        customerBuilder.setMobileNumber("9898989898");
        customerBuilder.setDateOfBirth("March 10, 2021");
        customerBuilder.setTokens(10);
        return customerBuilder.buildCustomer();
    }

    public Orders createDummyOrder(int status) {
        Orders orders = new Orders();
        orders.setCustomerId(1L);
        orders.setOrderAmount(1);
        orders.setOrderTime();
        orders.setMealId(1L);
        orders.setRestaurantId(1L);
        orders.setOrderStatus(status);
        return orders;
    }
}
