package com.dalhousie.MealStop.orders.model;

import com.dalhousie.MealStop.meal.model.Meal;
import com.dalhousie.MealStop.restaurant.model.Restaurant;
import com.dalhousie.MealStop.customer.model.Customer;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="orders")
public class Orders implements IOrders {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="customerId")
    private long customerId;

    @Column(name="restaurantId")
    private long restaurantId;

    @Column(name="mealId")
    private long mealId;


    @Column(name="status")
    private int status;

    @Column(name="amount")
    private float amount;

    @Column(name="orderDate")
    private Date date;

    @Override
    public int getOrderStatus(){
        return status;
    }

    @Override
    public void setOrderStatus(int status){
        this.status=status;
    }

    @Override
    public float getOrderAmount(){
        return amount;
    }

    @Override
    public void setOrderAmount(int amount){
        this.amount=amount;
    }

    @Override
    public Date getOrderTime(){
        return date;
    }

    @Override
    public void setOrderTime(){
        long millis=System.currentTimeMillis();
        this.date= new java.sql.Date(millis);
    }

    @Override
    public long getOrderId(){
        return id;
    }

    @Override
    public long getCustomerId(){
        return customerId;
    }

    @Override
    public long getRestaurantId(){
        return restaurantId;
    }

    @Override
    public long getMealId(){
        return mealId;
    }

    @Override
    public void setCustomerId(long customerId){
         this.customerId=customerId;
    }

    @Override
    public void setRestaurantId(long restaurantId){
        this.restaurantId=restaurantId;
    }

    @Override
    public void setMealId(long mealId){
        this.mealId=mealId;
    }

    @ManyToOne(fetch=FetchType.LAZY,optional=false)
    @JoinColumn(name = "restaurantId", referencedColumnName = "restaurantid", nullable = false,insertable = false,updatable = false)
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "customerId", referencedColumnName = "customer_id", nullable = false,insertable = false,updatable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "mealId", referencedColumnName = "id", nullable = false,insertable = false,updatable = false)
    private Meal meal;

    public Orders(){

    }
}






