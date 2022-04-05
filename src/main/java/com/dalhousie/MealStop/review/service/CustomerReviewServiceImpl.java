package com.dalhousie.MealStop.review.service;

import com.dalhousie.MealStop.restaurant.model.Restaurant;
import com.dalhousie.MealStop.customer.model.Customer;
import com.dalhousie.MealStop.review.model.CustomerReview;
import com.dalhousie.MealStop.review.repository.CustomerReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CustomerReviewServiceImpl implements ICustomerReviewService
{
    @Autowired
    CustomerReviewRepository customerReviewRepository;

    public void addReview(CustomerReview review)
    {
        customerReviewRepository.save(review);
    }

    @Override
    public CustomerReview getReviewById(Long id)
    {
        return customerReviewRepository.getById(id);
    }

    @Override
    public List<CustomerReview> getAllReviews()
    {
        List<CustomerReview> reviewList = customerReviewRepository.findAll();
        return reviewList;
    }

    @Override
    public List<CustomerReview> getReviewsOfCustomer(Customer customer)
    {
        List<CustomerReview> customerReviews= customerReviewRepository.findByCustomer(customer);
        return customerReviews;
    }

    @Override
    public List<CustomerReview> getReviewsOfRestaurant(Restaurant restaurant)
    {
        List<CustomerReview> customerReviews= customerReviewRepository.findByRestaurant(restaurant);
        return customerReviews;
    }

    @Override
    public void updateReview(Long id, CustomerReview modifiedReview)
    {
        CustomerReview existingReview = customerReviewRepository.getById(id);
        try
        {
            existingReview.setReviewDate(modifiedReview.getReviewDate());
            existingReview.setReviewMessage(modifiedReview.getReviewMessage());
            existingReview.setReviewScore(modifiedReview.getReviewScore());
        }
        catch(Exception e)
        {
           log.error(e.getMessage());
        }
        customerReviewRepository.save(existingReview);
    }

    @Override
    public void deleteReviewById(Long id)
    {
        customerReviewRepository.deleteById(id);
    }
}
