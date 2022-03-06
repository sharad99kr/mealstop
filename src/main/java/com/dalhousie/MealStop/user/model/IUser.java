package com.dalhousie.MealStop.user.model;

public interface IUser
{
    public long getId();

    public String getFirstName();

    public void setFirstName(String firstName);

    public String getLastName();

    public void setLastName(String lastName);

    public String  getEmail();

    public void setEmail(String email);

    public void setPassword(String password);

    public String getPassword();

    public Integer getUserType();

    public void setUserType(Integer userType);
}
