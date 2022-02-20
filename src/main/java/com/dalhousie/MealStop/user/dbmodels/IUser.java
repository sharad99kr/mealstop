package com.dalhousie.MealStop.user.dbmodels;

import java.util.Date;

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

    Date getDateOfBirth();

    void setDateOfBirth(Date dateOfBirth);

    Integer getUserType();

    void setUserType(Integer userType);

}
