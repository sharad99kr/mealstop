package com.dalhousie.MealStop.ngo.modal;

import com.dalhousie.MealStop.domainconstants.MealStopConstants;
import com.dalhousie.MealStop.user.entity.User;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;

@Entity
@Table(name = "ngo")
public class NGO implements INGO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
        this.totalOrders = MealStopConstants.NGO_DEFAULT_ORDERS;
    }

    public NGO(User user)
    {
        this.id = user.getUser_id();
        this.name=user.getFirstName();
        this.email=user.getUsername();
        this.address = user.getAddress();
        this.phoneNumber = user.getMobileNumber();
        this.totalOrders = MealStopConstants.NGO_DEFAULT_ORDERS;
    }




}