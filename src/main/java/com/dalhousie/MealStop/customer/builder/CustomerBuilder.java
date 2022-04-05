package com.dalhousie.MealStop.customer.builder;

import com.dalhousie.MealStop.customer.model.Customer;

public class CustomerBuilder
{
    private final Customer customer;

    public CustomerBuilder() {
        customer=new Customer();
    }

    public void setId(long id) {
        this.customer.setId(id);
    }

    public void setFirstName(String firstName) {
        this.customer.setFirstName(firstName);
    }

    public void setLastName(String lastName) {
        this.customer.setLastName(lastName);
    }

    public void setEmail(String email) {
        this.customer.setEmail(email);
    }

    public void setMobileNumber(String mobileNumber) {
        this.customer.setMobileNumber(mobileNumber);
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.customer.setDateOfBirth(dateOfBirth);
    }

    public void setAddress(String address) {
        this.customer.setAddress(address);
    }

    public void setTokens(Integer tokens) {
        this.customer.setTokens(tokens);
    }

    public Customer buildCustomer() {
        return customer;
    }
}
