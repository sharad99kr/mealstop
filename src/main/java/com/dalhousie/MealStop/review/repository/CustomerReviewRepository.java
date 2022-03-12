package com.dalhousie.MealStop.review.repository;

import com.dalhousie.MealStop.Restaurant.model.Restaurant;
import com.dalhousie.MealStop.customer.modal.Customer;
import com.dalhousie.MealStop.review.modal.CustomerReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CustomerReviewRepository extends JpaRepository<CustomerReview, Long>
{
    List<CustomerReview> findById(String firstName);
    List<CustomerReview> findByCustomer(Customer customer);
    List<CustomerReview> findByRestaurant(Restaurant restaurant);
}
