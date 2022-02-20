package com.dalhousie.MealStop.user.dbmodels;

import java.util.Date;

public class User implements IUser {

    private long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;
    private Date dateOfBirth;
    private Integer userType;

    public User(long userId) {
        this.userId = userId;
    }

    @Override
    public long getUserId() {
        return this.userId;
    }

    @Override
    public String getFirstName() {
        return this.firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getLastName() {
        return this.lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getMobileNumber() {
        return this.mobileNumber;
    }

    @Override
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    @Override
    public Date getDateOfBirth() {
        return this.dateOfBirth;
    }

    @Override
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public Integer getUserType() {
        return this.userType;
    }

    @Override
    public void setUserType(Integer userType) {
        this.userType = userType;
    }
}
