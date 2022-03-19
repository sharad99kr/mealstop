package com.dalhousie.MealStop.favorites.repository;

import java.util.List;
import com.dalhousie.MealStop.Restaurant.model.Restaurant;
import com.dalhousie.MealStop.customer.modal.Customer;
import com.dalhousie.MealStop.favorites.modal.CustomerFavorites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerFavoritesRepository extends JpaRepository<CustomerFavorites, Long>
{
    List<CustomerFavorites> findByCustomer(Customer customer);
    CustomerFavorites findByCustomerAndRestaurant(Customer customer, Restaurant restaurant);
}