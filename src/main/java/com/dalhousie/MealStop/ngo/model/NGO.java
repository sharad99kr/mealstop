package com.dalhousie.MealStop.ngo.model;

import com.dalhousie.MealStop.common.CommonConstants;
import com.dalhousie.MealStop.user.entity.User;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Column;

@Entity
@Table(name = "ngo")
public class NGO implements INGO {
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "ngo_name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "total_orders")
    private Integer totalOrders;

    public NGO() {}

    public NGO(String name, String email, String address, String phoneNumber)
    {
        this.name = name;
        this.email=email;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.totalOrders = CommonConstants.NGO_DEFAULT_ORDERS;
    }

    public NGO(User user)
    {
        this.id = user.getUser_id();
        this.name=user.getFirstName();
        this.email=user.getUsername();
        this.address = user.getAddress();
        this.phoneNumber = user.getMobileNumber();
        this.totalOrders = CommonConstants.NGO_DEFAULT_ORDERS;
    }

    @Override
    public long getId()
    {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getNGOName()
    {
        return name;
    }

    @Override
    public void setNGOName(String name)
    {
        this.name = name;
    }

    @Override
    public String getEmail()
    {
        return email;
    }

    @Override
    public void setEmail(String email)
    {
        this.email = email;
    }

    public Integer getTotalOrders()
    {
        return totalOrders;
    }

    public void setTotalOrders(Integer totalOrders)
    {
        this.totalOrders = totalOrders;
    }
}