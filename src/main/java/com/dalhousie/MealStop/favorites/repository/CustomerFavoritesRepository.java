package com.dalhousie.MealStop.favorites.repository;

import com.dalhousie.MealStop.customer.modal.Customer;
import com.dalhousie.MealStop.favorites.modal.CustomerFavorites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerFavoritesRepository extends JpaRepository<Long, CustomerFavorites>
{
    CustomerFavorites findByCustomer(Customer customer);
}
