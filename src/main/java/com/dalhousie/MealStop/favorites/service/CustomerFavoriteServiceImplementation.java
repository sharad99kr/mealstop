package com.dalhousie.MealStop.favorites.service;

import com.dalhousie.MealStop.restaurant.model.Restaurant;
import com.dalhousie.MealStop.restaurant.service.IRestaurantService;
import com.dalhousie.MealStop.customer.model.Customer;
import com.dalhousie.MealStop.customer.service.ICustomerService;
import com.dalhousie.MealStop.favorites.model.CustomerFavorites;
import com.dalhousie.MealStop.favorites.repository.CustomerFavoritesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerFavoriteServiceImplementation implements ICustomerFavoriteService
{
    @Autowired
    private CustomerFavoritesRepository cusFavRepo;

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IRestaurantService restaurantService;

    @Override
    public List<CustomerFavorites> getCustomerFavorites()
    {
        Customer loggedInCustomer = customerService.getCustomerDetailsFromSession();
        List<CustomerFavorites> customerFavorites =  cusFavRepo.findByCustomer(loggedInCustomer);
        return customerFavorites;
    }

    @Override
    public void addRestaurantToCustomerFavorites(Long restaurantId)
    {
        Customer cust = customerService.getCustomerDetailsFromSession();
        Restaurant favRes = restaurantService.getRestaurantById(restaurantId);
        CustomerFavorites customerFavorites =  cusFavRepo.findByCustomerAndRestaurant(cust, favRes);

        if(customerFavorites == null)
        {
            customerFavorites = new CustomerFavorites(cust, favRes);
            cusFavRepo.save(customerFavorites);
        }
    }

    @Override
    public int getRestaurantFavorites(Long restaurantId)
    {
        Restaurant favRes = restaurantService.getRestaurantById(restaurantId);
        return  cusFavRepo.findByRestaurant(favRes).size();
    }

    @Override
    public void deleteCustomerFavoriteById(Long customerFavoriteId)
    {
        cusFavRepo.deleteById(customerFavoriteId);
    }
}
