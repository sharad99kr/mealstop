package com.dalhousie.MealStop.review.model;

import com.dalhousie.MealStop.restaurant.model.Restaurant;
import com.dalhousie.MealStop.customer.model.Customer;

import java.util.Date;

public interface ICustomerReview
{
    long getId();

    void setId(long id);

    Integer getReviewScore() ;

    void setReviewScore(Integer reviewScore);

    String getReviewMessage();

    void setReviewMessage(String reviewMessage);

    Date getReviewDate() ;

    void setReviewDate(Date reviewDate);

    Customer getCustomer() ;

    void setCustomer(Customer customer) ;

    Restaurant getRestaurant();

    void setRestaurant(Restaurant restaurant);
}
