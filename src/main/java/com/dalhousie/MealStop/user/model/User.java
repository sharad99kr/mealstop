package com.dalhousie.MealStop.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User implements  IUser
{
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

    @Override
    public long getId()
    {
        return id;
    }

    @Override
    public String getFirstName()
    {
        return firstName;
    }

    @Override
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    @Override
    public String getLastName()
    {
        return lastName;
    }

    @Override
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
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
    public String getMobileNumber()
    {
        return mobileNumber;
    }

    @Override
    public void setMobileNumber(String mobileNumber)
    {
        this.mobileNumber=mobileNumber;
    }

    @Override
    public String getDateOfBirth()
    {
        return this.dateOfBirth;
    }

    @Override
    public void setDateOfBirth(String dateOfBirth)
    {
        this.dateOfBirth=dateOfBirth;
    }

    @Override
    public Integer getUserType()
    {
        return getUserType();
    }

    @Override
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