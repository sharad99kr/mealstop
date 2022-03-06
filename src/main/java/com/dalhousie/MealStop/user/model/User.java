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
    @Column(name="user_id")
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name= "user_type")
    private Integer userType;

    public User(String firstName, String lastName, String email, String password, Integer userType)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password=password;
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
    public void setPassword(String password)
    {
        this.password=password;
    }

    @Override
    public String getPassword()
    {
        return this.password;
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
        sb.append(", password="+password);
        sb.append(", usertype=" + userType + "]");
        return sb.toString();
    }
}