package com.dalhousie.MealStop.customer.modal;

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

    public Integer getTokens();

    public void setTokens(Integer tokens);
}
