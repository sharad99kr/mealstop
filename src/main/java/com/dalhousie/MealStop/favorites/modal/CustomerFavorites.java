package com.dalhousie.MealStop.favorites.modal;

import com.dalhousie.MealStop.Restaurant.model.Restaurant;
import com.dalhousie.MealStop.customer.modal.Customer;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "customer_favorite")
public class CustomerFavorites
{
    @Id
    @Column(name="Id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id", nullable = false)
    private Customer customer;

    @ManyToMany
    @JoinColumn(name = "restaurant_id", referencedColumnName = "restaurantid", nullable = false)
    private List<Restaurant> restaurants;

    public CustomerFavorites()
    {

    }

    public CustomerFavorites(Customer customer, List<Restaurant> restaurant)
    {
        this.customer = customer;
        this.restaurants = restaurant;
    }

    public Customer getCustomer()
    {
        return this.customer;
    }

    public void setCustomer(Customer customer)
    {
        this.customer=customer;
    }

    public List<Restaurant> getRestaurants()
    {
        return this.restaurants;
    }

    public void setRestaurants(List<Restaurant> restaurants)
    {
        this.restaurants=restaurants;
    }
}
