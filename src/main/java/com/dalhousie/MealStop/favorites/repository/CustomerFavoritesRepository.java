package com.dalhousie.MealStop.favorites.repository;

import java.util.List;
import com.dalhousie.MealStop.restaurant.model.Restaurant;
import com.dalhousie.MealStop.customer.model.Customer;
import com.dalhousie.MealStop.favorites.model.CustomerFavorites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerFavoritesRepository extends JpaRepository<CustomerFavorites, Long>
{
    List<CustomerFavorites> findByCustomer(Customer customer);
    CustomerFavorites findByCustomerAndRestaurant(Customer customer, Restaurant restaurant);
    List<CustomerFavorites> findByRestaurant(Restaurant restaurant);
}
