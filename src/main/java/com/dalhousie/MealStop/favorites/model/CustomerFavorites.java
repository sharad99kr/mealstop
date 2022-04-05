package com.dalhousie.MealStop.favorites.model;

import com.dalhousie.MealStop.restaurant.model.Restaurant;
import com.dalhousie.MealStop.customer.model.Customer;

import javax.persistence.*;

@Entity
@Table(name = "customer_favorite")
public class CustomerFavorites implements ICustomerFavorites {
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

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id=id;
    }

    @Override
    public Customer getCustomer() {
        return this.customer;
    }

    @Override
    public void setCustomer(Customer customer) {
        this.customer=customer;
    }

    @Override
    public Restaurant getRestaurant() {
        return this.restaurant;
    }

    @Override
    public void setRestaurant(Restaurant restaurant) {
        this.restaurant=restaurant;
    }
}
