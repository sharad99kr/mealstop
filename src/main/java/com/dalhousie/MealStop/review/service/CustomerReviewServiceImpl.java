package com.dalhousie.MealStop.review.service;

import com.dalhousie.MealStop.Restaurant.model.Restaurant;
import com.dalhousie.MealStop.customer.modal.Customer;
import com.dalhousie.MealStop.review.modal.CustomerReview;
import com.dalhousie.MealStop.review.repository.CustomerReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerReviewServiceImpl implements CustomerReviewService
{
    @Autowired
    CustomerReviewRepository customerReviewRepository;

    public void addReview(CustomerReview review)
    {
        customerReviewRepository.save(review);
    }

    public List<CustomerReview> getAllReviews()
    {
        List<CustomerReview> reviewList = customerReviewRepository.findAll();
        return reviewList;
    }

    public List<CustomerReview> getReviewsOfCustomer(Customer customer)
    {
        List<CustomerReview> customerReviews= customerReviewRepository.findByCustomer(customer);
        return customerReviews;
    }

    public List<CustomerReview> getReviewsOfRestaurant(Restaurant restaurant)
    {
        List<CustomerReview> customerReviews= customerReviewRepository.findByRestaurant(restaurant);
        return customerReviews;
    }

    public void deleteReviewById(Long id)
    {
        customerReviewRepository.deleteById(id);
    }
}
