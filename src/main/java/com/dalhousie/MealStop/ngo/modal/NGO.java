package com.dalhousie.MealStop.ngo.modal;
import com.dalhousie.MealStop.user.model.IUser;
import com.dalhousie.MealStop.user.model.User;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;

@Entity
@Table(name = "ngo")
public class NGO implements INGO
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "ngo_name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "number_of_orders")
    private Integer orders;

    public NGO() {}

    public NGO(String name, String email)
    {
        this.name = name;
        this.email = email;
        this.orders = 0;
    }

    public NGO(User user)
    {
        //ToDo need to check if the user is of ngo type.
        this.id = user.getId();
        this.name=user.getFirstName();
        this.email=user.getEmail();
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

    @Override
    public Integer setTotalOrders(int orderCount) {
       return this.orders = orderCount;
    }

    @Override
    public int getTotalOrders()
    {
        return orders;
    }

    @Override
    public String toString()
    {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + name + '\'' +
                ", email='" + email + '\'' +
                ", total orders='" + orders + '\'' +
                '}';
    }
}
