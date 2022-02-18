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

    public String getMobileNumber();

    public void setMobileNumber(String mobileNumber);

    public String getDateOfBirth();

    public void setDateOfBirth(String dateOfBirth);

    public Integer getUserType();

    public void setUserType(Integer userType);
}
