package com.dalhousie.MealStop.customer.service;

import com.dalhousie.MealStop.customer.model.Customer;
import com.dalhousie.MealStop.user.entity.User;

import java.util.List;

public interface ICustomerService
{
    public Customer getCustomerById(String id);
    public List<Customer> getAllCustomers();
    public Customer getCustomerDetailsFromSession();
    public void addCustomer(User user);
    public void addCustomer(Customer newCustomer);
    public Long getLoggedInCustomerId();
    public Integer getCustomerTokenCount();
    public Integer decrementCustomerToken(Integer tokenCount);
    public void incrementCustomerToken(Integer tokenCount);
    public Customer getCustomerInstanceFromUser(User user);
}
