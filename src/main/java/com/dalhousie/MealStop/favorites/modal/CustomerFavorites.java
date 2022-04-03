package com.dalhousie.MealStop.favorites.modal;

import com.dalhousie.MealStop.restaurant.model.Restaurant;
import com.dalhousie.MealStop.customer.modal.Customer;

import javax.persistence.*;

@Entity
@Table(name = "customer_favorite")
public class CustomerFavorites
{
    @Id
    @Column(name="Id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", referencedColumnName = "restaurantid", nullable = false)
    private Restaurant restaurant;

    public CustomerFavorites()
    {

    }

    public CustomerFavorites(Customer customer, Restaurant restaurant)
    {
        this.customer = customer;
        this.restaurant = restaurant;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public Customer getCustomer()
    {
        return this.customer;
    }

    public void setCustomer(Customer customer)
    {
        this.customer=customer;
    }

    public Restaurant getRestaurant()
    {
        return this.restaurant;
    }

    public void setRestaurant(Restaurant restaurant)
    {
        this.restaurant=restaurant;
    }
}
