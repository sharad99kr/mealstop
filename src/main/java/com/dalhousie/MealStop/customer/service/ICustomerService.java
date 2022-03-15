package com.dalhousie.MealStop.customer.service;

import com.dalhousie.MealStop.customer.modal.Customer;

public interface ICustomerService
{
    public Customer getCustomerById(String id);
    public Customer getCustomerDetailsFromSession();
}
