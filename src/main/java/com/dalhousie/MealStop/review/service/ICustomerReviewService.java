package com.dalhousie.MealStop.review.service;

import com.dalhousie.MealStop.restaurant.model.Restaurant;
import com.dalhousie.MealStop.customer.model.Customer;
import com.dalhousie.MealStop.review.model.CustomerReview;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ICustomerReviewService
{
    void addReview(CustomerReview review);
    CustomerReview getReviewById(Long id);
    List<CustomerReview> getAllReviews();
    List<CustomerReview> getReviewsOfCustomer(Customer customer);
    List<CustomerReview> getReviewsOfRestaurant(Restaurant restaurant);
    void deleteReviewById(Long id);
    void updateReview(Long id, CustomerReview customerReview);
}
