package com.dalhousie.MealStop.Restaurant.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "restaurant")
public class Restaurant implements IRestaurant{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String restaurantName;

    @Column(name = "restaurantusername")
    private String restaurantUsername;

    @Column(name = "restaurantpassword")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name ="phonenumber")
    private String phoneNumber;

    @Column(name= "address")
    private String address;

    @Column(name= "accountstatus")
    private Integer accountStatus;

    public Restaurant(String restaurantName, String restaurantUsername, String password, String email, String phoneNumber, String address, int accountStatus)
    {
        this.restaurantName = restaurantName;
        this.restaurantUsername = restaurantUsername;
        this.password = password;
        this.email=email;
        this.phoneNumber=phoneNumber;
        this.address=address;
        this.accountStatus = accountStatus;
    }

    @Override
    public long getId()
    {
        return id;
    }

    @Override
    public String getRestaurantName()
    {
        return restaurantName;
    }

    @Override
    public void setRestaurantName(String restaurantName)
    {
        this.restaurantName = restaurantName;
    }

    @Override
    public String getRestaurantUsername()
    {
        return restaurantUsername;
    }

    @Override
    public void setRestaurantUsername(String restaurantUsername)
    {
        this.restaurantUsername = restaurantUsername;
    }

    @Override
    public String getPassword()
    {
        return this.password;
    }

    @Override
    public void setPassword(String password)
    {
        this.password=password;
    }

    @Override
    public String  getEmail()
    {
        return email;
    }

    @Override
    public void setEmail(String email)
    {
        this.email = email;
    }

    @Override
    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    @Override
    public void setPhoneNumber(String mobileNumber)
    {
        this.phoneNumber=phoneNumber;
    }

    @Override
    public String getAddress()
    {
        return this.address;
    }

    @Override
    public void setAddress(String address)
    {
        this.address=address;
    }

    @Override
    public Integer getAccountStatus()
    {
        return this.accountStatus;
    }

    @Override
    public void setAccountStatus(Integer accountStatus)
    {
        this.accountStatus=accountStatus;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Restaurant [id=" + id);
        sb.append(", restaurantName=" + restaurantName);
        sb.append(", restaurantUsername=" + restaurantUsername);
        sb.append(", password=" + password);
        sb.append(", email=" + email);
        sb.append(", phoneNumber=" + phoneNumber);
        sb.append(", address=" + address);
        sb.append(", accountStatus=" + accountStatus + "]");
        return sb.toString();
    }
}