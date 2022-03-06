package com.dalhousie.MealStop.review.service;

import com.dalhousie.MealStop.review.modal.CustomerReview;
import com.dalhousie.MealStop.review.repository.CustomerReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerReviewServiceImpl
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
}
