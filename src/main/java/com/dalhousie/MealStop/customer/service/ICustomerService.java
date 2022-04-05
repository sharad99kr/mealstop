package com.dalhousie.MealStop.customer.service;

import com.dalhousie.MealStop.customer.model.Customer;
import com.dalhousie.MealStop.user.entity.User;

import java.util.List;

public interface ICustomerService
{
     Customer getCustomerById(String id);
     List<Customer> getAllCustomers();
     Customer getCustomerDetailsFromSession();
     void addCustomer(User user);
     void addCustomer(Customer newCustomer);
     Long getLoggedInCustomerId();
     Integer getCustomerTokenCount();
     Integer decrementCustomerToken(Integer tokenCount);
     void incrementCustomerToken(Integer tokenCount);
     Customer getCustomerInstanceFromUser(User user);
}
