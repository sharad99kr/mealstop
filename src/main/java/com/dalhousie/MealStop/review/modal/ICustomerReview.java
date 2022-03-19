package com.dalhousie.MealStop.review.modal;

import com.dalhousie.MealStop.Restaurant.model.Restaurant;
import com.dalhousie.MealStop.customer.modal.Customer;

import java.util.Date;

public interface ICustomerReview
{
    public long getId();

    public void setId(long id);

    public Integer getReviewScore() ;

    public void setReviewScore(Integer reviewScore);

    public String getReviewMessage();

    public void setReviewMessage(String reviewMessage);

    public Date getReviewDate() ;

    public void setReviewDate(Date reviewDate);

    public Customer getCustomer() ;

    public void setCustomer(Customer customer) ;

    public Restaurant getRestaurant();

    public void setRestaurant(Restaurant restaurant);
}
