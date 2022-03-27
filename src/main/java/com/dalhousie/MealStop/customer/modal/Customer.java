package com.dalhousie.MealStop.customer.modal;

import com.dalhousie.MealStop.domainconstants.MealStopConstants;
import com.dalhousie.MealStop.user.entity.User;
import com.dalhousie.MealStop.user.models.UserModel;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;

@Entity
@Table(name = "customer")
public class Customer implements ICustomer
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    public Customer() {}

    public Customer(String firstName, String lastName, String email)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.tokens = MealStopConstants.CUSTOMER_DEFAULT_TOKENS;
    }

    public Customer(User user)
    {
        //ToDo need to check if the user is of customer type.
        this.id = user.getUser_id();
        this.firstName=user.getFirstName();
        this.lastName=user.getLastName();
        this.email=user.getUsername();
        this.tokens = MealStopConstants.CUSTOMER_DEFAULT_TOKENS;
    }

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
    public Integer getTokens() {
        return tokens;
    }

    @Override
    public void setTokens(Integer tokens) {
        this.tokens = tokens;
    }

    @Override
    public String toString()
    {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", tokens='" + tokens + '\'' +
                '}';
    }
}
