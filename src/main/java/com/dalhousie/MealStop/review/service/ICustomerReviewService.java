package com.dalhousie.MealStop.review.service;

import com.dalhousie.MealStop.Restaurant.model.Restaurant;
import com.dalhousie.MealStop.customer.modal.Customer;
import com.dalhousie.MealStop.review.modal.CustomerReview;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ICustomerReviewService
{
    public void addReview(CustomerReview review);
    public CustomerReview getReviewById(Long id);
    public List<CustomerReview> getAllReviews();
    public List<CustomerReview> getReviewsOfCustomer(Customer customer);
    public List<CustomerReview> getReviewsOfRestaurant(Restaurant restaurant);
    public void deleteReviewById(Long id);
    public void updateReview(Long id, CustomerReview customerReview);
}
