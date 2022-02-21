package com.dalhousie.MealStop.user.dbmodels;

public interface IUser {

    long getUserId();

    String getFirstName();

    void setFirstName(String firstName);

    String getLastName();

    void setLastName(String lastName);

    String getEmail();

    void setEmail(String email);

    String getMobileNumber();

    void setMobileNumber(String mobileNumber);

    String getDateOfBirth();

    void setDateOfBirth(String dateOfBirth);

    Integer getUserType();

    void setUserType(Integer userType);

    String getAddress();

    void setAddress(String address);

}
