package com.dalhousie.MealStop.review.service;

import com.dalhousie.MealStop.Restaurant.model.Restaurant;
import com.dalhousie.MealStop.customer.modal.Customer;
import com.dalhousie.MealStop.review.modal.CustomerReview;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerReviewService
{
    public void addReview(CustomerReview review);
    public List<CustomerReview> getAllReviews();
    public List<CustomerReview> getReviewsOfCustomer(Customer customer);
    public List<CustomerReview> getReviewsOfRestaurant(Restaurant restaurant);
}
