package com.dalhousie.MealStop.Restaurant.model;

import com.dalhousie.MealStop.Meal.model.Meal;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "restaurant")
@NoArgsConstructor
public class Restaurant implements IRestaurant{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "restaurantid")
    private long id;

    @Column(name = "name")
    private String restaurantName;

    @Column(name = "userid")
    private long userid;

    @Column(name = "availability")
    private String availability;

    @Column(name = "email")
    private String email;

    @Column(name ="phonenumber")
    private String phoneNumber;

    @Column(name= "address")
    private String address;

    @Column(name= "accountstatus")
    private Integer accountStatus;

    @OneToMany(mappedBy = "restaurant")
    private List<Meal> meals;

    public Restaurant(String restaurantName, long userID, String availability, String email, String phoneNumber, String address, int accountStatus)
    {
        this.restaurantName = restaurantName;
        this.userid = userID;
        this.availability = availability;
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
    public long getUserId()
    {
        return userid;
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
        sb.append(", userID=" + userid);
        sb.append(", restaurantName=" + restaurantName);
        sb.append(", availability=" + availability);
//        sb.append(", password=" + password);
        sb.append(", email=" + email);
        sb.append(", phoneNumber=" + phoneNumber);
        sb.append(", address=" + address);
        sb.append(", accountStatus=" + accountStatus + "]");
        return sb.toString();
    }
}