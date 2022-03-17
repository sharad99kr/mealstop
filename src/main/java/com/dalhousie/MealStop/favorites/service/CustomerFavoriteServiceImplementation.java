package com.dalhousie.MealStop.favorites.service;

import com.dalhousie.MealStop.customer.modal.Customer;
import com.dalhousie.MealStop.customer.service.ICustomerService;
import com.dalhousie.MealStop.favorites.modal.CustomerFavorites;
import com.dalhousie.MealStop.favorites.repository.CustomerFavoritesRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomerFavoriteServiceImplementation implements CustomerFavoriteService
{
    @Autowired
    private CustomerFavoritesRepository customerFavoritesRepository;

    @Autowired
    private ICustomerService customerService;

    @Override
    public CustomerFavorites getCustomerFavorites()
    {
        Customer loggedInCustomer = customerService.getCustomerDetailsFromSession();
        return customerFavoritesRepository.findByCustomer(loggedInCustomer);
    }
}
