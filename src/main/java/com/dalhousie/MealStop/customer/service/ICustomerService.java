package com.dalhousie.MealStop.customer.service;

import com.dalhousie.MealStop.customer.modal.Customer;
import com.dalhousie.MealStop.user.entity.User;

public interface ICustomerService
{
    public Customer getCustomerById(String id);
    public Customer getCustomerDetailsFromSession();
    public void addCustomer(User user);
    public Long getLoggedInCustomerId();
    public Integer getCustomerTokenCount();
    public void decrementCustomerToken(Integer tokenCount);
}
