package com.dalhousie.MealStop.review.builder;

import com.dalhousie.MealStop.customer.model.Customer;
import com.dalhousie.MealStop.restaurant.model.Restaurant;
import com.dalhousie.MealStop.review.model.CustomerReview;

import java.util.Date;

public class CustomerReviewBuilder
{
    private CustomerReview customerReview;

    public CustomerReviewBuilder()
    {
        customerReview = new CustomerReview();
    }

    public void setId(long id) {
        customerReview.setId(id);
    }

    public void setReviewScore(Integer reviewScore) {
        customerReview.setReviewScore(reviewScore);
    }

    public void setReviewMessage(String reviewMessage) {
        customerReview.setReviewMessage(reviewMessage);
    }

    public void setReviewDate(Date reviewDate) {
        customerReview.setReviewDate(reviewDate);
    }

    public void setCustomer(Customer customer) {
        customerReview.setCustomer(customer);
    }

    public void setRestaurant(Restaurant restaurant) {
        customerReview.setRestaurant(restaurant);
    }

    public CustomerReview buildCustomerReview() {
        return customerReview;
    }
}
