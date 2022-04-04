package com.dalhousie.MealStop.review.repository;

import com.dalhousie.MealStop.restaurant.model.Restaurant;
import com.dalhousie.MealStop.customer.model.Customer;
import com.dalhousie.MealStop.review.model.CustomerReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CustomerReviewRepository extends JpaRepository<CustomerReview, Long>
{
    List<CustomerReview> findByCustomer(Customer customer);
    List<CustomerReview> findByRestaurant(Restaurant restaurant);
}
