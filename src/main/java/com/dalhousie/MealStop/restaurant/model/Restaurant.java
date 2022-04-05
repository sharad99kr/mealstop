package com.dalhousie.MealStop.restaurant.model;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "restaurant")
public class Restaurant implements IRestaurant{

    public Restaurant(){
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "restaurantid")
    private long id;

    @NotEmpty (message = "{Null.Restaurant.Name}")
    @Column(name = "name")
    private String restaurantName;

    @Column(name = "userid")
    private long userid;

    @NotEmpty (message = "{Null.Restaurant.Availability}")
    @Column(name = "availability")
    private String availability;

    @NotEmpty(message = "{Null.Restaurant.Email}")
    @Email(message = "{Size.Restaurant.Email}")
    @Column(name = "email")
    private String email;

    @NotEmpty(message = "{Null.Restaurant.PhoneNumber}")
    @Size(min = 10, max = 10, message = "{Size.Restaurant.PhoneNumber}")
    @Column(name ="phonenumber")
    private String phoneNumber;

    @NotEmpty(message = "{Null.Restaurant.Address}")
    @Size(min=10, max = 200, message = "{Size.Restaurant.Address}")
    @Column(name= "address")
    private String address;

    @Transient
    private String reviewScore;

    @Override
    public long getId()
    {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public long getUserId()
    {
        return userid;
    }

    @Override
    public void setUserId(long userid)
    {
        this.userid = userid;
    }

    @Override
    public String getAvailability()
    {
        return availability;
    }

    @Override
    public void setAvailability(String availability)
    {
        this.availability = availability;
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
    public void setPhoneNumber(String phoneNumber)
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
    @Transient
    public String getAvgReviewScore()
    {
        return this.reviewScore;
    }

    @Override
    @Transient
    public void setAvgReviewScore(String reviewScore)
    {
        this.reviewScore=reviewScore;
    }
}