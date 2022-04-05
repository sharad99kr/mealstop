package com.dalhousie.MealStop.customer.model;

import com.dalhousie.MealStop.common.CommonConstants;
import com.dalhousie.MealStop.user.entity.User;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Column;

@Entity
@Table(name = "customer")
public class Customer implements ICustomer {
    @Id
    @Column(name = "customer_id")
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "tokens")
    private Integer tokens;

    @Column(name= "mobilenumber")
    private String mobileNumber;

    @Column(name= "dateofbirth")
    private String dateOfBirth;

    @Column(name= "address")
    private String address;

    public Customer() {
    }

    public Customer(User user) {
        this.id = user.getUser_id();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getUsername();
        this.mobileNumber = user.getMobileNumber();
        this.dateOfBirth = user.getDateOfBirth();
        this.address = user.getAddress();
        this.tokens = CommonConstants.CUSTOMER_DEFAULT_TOKENS;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getMobileNumber() {
        return mobileNumber;
    }

    @Override
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    @Override
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    @Override
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public Integer getTokens() {
        return tokens;
    }

    @Override
    public void setTokens(Integer tokens) {
        this.tokens = tokens;
    }
}
