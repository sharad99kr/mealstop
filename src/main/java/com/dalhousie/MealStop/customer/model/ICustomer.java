package com.dalhousie.MealStop.customer.model;

public interface ICustomer
{
     long getId();

     void setId(long id);

     String getFirstName();

     void setFirstName(String firstName);

     String getLastName();

     void setLastName(String lastName);

     String  getEmail();

     void setEmail(String email);

     void setMobileNumber(String mobileNumber);

     String getMobileNumber();

     String getDateOfBirth();

     void setDateOfBirth(String dateOfBirth);

     void setAddress(String address);

     String getAddress();

     Integer getTokens();

     void setTokens(Integer tokens);
}
