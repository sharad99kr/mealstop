package com.dalhousie.MealStop.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name ="mobilenumber")
    private String mobileNumber;

    @Column(name= "dateofbirth")
    private String dateOfBirth;

    @Column(name= "usertype")
    private Integer userType;

    public User(String firstName, String lastName, String email, String mobileNumber, String dateOfBirth, Integer userType)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobileNumber=mobileNumber;
        this.dateOfBirth=dateOfBirth;
        this.userType=userType;
    }

    public long getId()
    {
        return id;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String  getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getMobileNumber()
    {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber)
    {
        this.mobileNumber=mobileNumber;
    }

    public String getDateOfBirth()
    {
        return this.dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth)
    {
        this.dateOfBirth=dateOfBirth;
    }

    public Integer getUserType()
    {
        return getUserType();
    }

    public void setUserType(Integer userType)
    {
        this.userType=userType;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("User [id=" + id);
        sb.append(", firstname=" + firstName);
        sb.append(", lastName=" + lastName);
        sb.append(", email=" + email);
        sb.append(", mobiilenumber=" + mobileNumber);
        sb.append(", dateofbirth=" + dateOfBirth);
        sb.append(", usertype=" + userType + "]");
        return sb.toString();
    }
}