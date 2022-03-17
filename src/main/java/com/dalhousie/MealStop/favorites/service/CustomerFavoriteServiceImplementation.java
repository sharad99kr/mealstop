package com.dalhousie.MealStop.favorites.service;

import com.dalhousie.MealStop.Restaurant.model.Restaurant;
import com.dalhousie.MealStop.Restaurant.service.RestaurantService;
import com.dalhousie.MealStop.customer.modal.Customer;
import com.dalhousie.MealStop.customer.service.ICustomerService;
import com.dalhousie.MealStop.favorites.modal.CustomerFavorites;
import com.dalhousie.MealStop.favorites.repository.CustomerFavoritesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerFavoriteServiceImplementation implements CustomerFavoriteService
{
    @Autowired
    private CustomerFavoritesRepository customerFavoritesRepository;

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private RestaurantService restaurantService;

    @Override
    public List<CustomerFavorites> getCustomerFavorites()
    {
        Customer loggedInCustomer = customerService.getCustomerDetailsFromSession();
        List<CustomerFavorites> customerFavorites =  customerFavoritesRepository.findByCustomer(loggedInCustomer);
        return customerFavorites;
    }

    @Override
    public void addRestaurantToCustomerFavorites(Long restaurantId)
    {
        Customer loggedInCustomer = customerService.getCustomerDetailsFromSession();
        Restaurant favoriteRestaurant = restaurantService.getRestaurantById(restaurantId);
        CustomerFavorites customerFavorites =  customerFavoritesRepository.findByCustomerAndRestaurant(loggedInCustomer, favoriteRestaurant);

        if(isCustomerFavoritesNotPresent(customerFavorites))
        {
            System.err.println("New favorite added to the user");
            customerFavorites = new CustomerFavorites(loggedInCustomer, favoriteRestaurant);
            customerFavoritesRepository.save(customerFavorites);
        }
        else
        {
            System.err.println("This restaurant is already a favorite for the customer");
        }
    }

    @Override
    public void deleteCustomerFavoriteById(Long customerFavoriteId)
    {
        customerFavoritesRepository.deleteById(customerFavoriteId);
    }

    private boolean isCustomerFavoritesPresent(CustomerFavorites customerFavorites)
    {
        return customerFavorites!=null;
    }

    private boolean isCustomerFavoritesNotPresent(CustomerFavorites customerFavorites)
    {
        return customerFavorites==null;
    }
}
