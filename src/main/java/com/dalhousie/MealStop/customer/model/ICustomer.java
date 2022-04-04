package com.dalhousie.MealStop.customer.model;

public interface ICustomer
{
    public long getId();

    public void setId(long id);

    public String getFirstName();

    public void setFirstName(String firstName);

    public String getLastName();

    public void setLastName(String lastName);

    public String  getEmail();

    public void setEmail(String email);

    public void setMobileNumber(String mobileNumber);

    public String getMobileNumber();

    public String getDateOfBirth();

    public void setDateOfBirth(String dateOfBirth);

    public void setAddress(String address);

    public String getAddress();

    public Integer getTokens();

    public void setTokens(Integer tokens);
}
