package com.dalhousie.MealStop.orders.model;

import javax.persistence.*;

@Entity
@Table(name="order")
public class Order implements IOrder  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long order_id;

    @Column(name="customer_id")
    private long customer_id;

    @Column(name="restaurant_id")
    private long restaurant_id;

    @Column(name="meal_id")
    private long meal_id;

    @Column(name="payment_id")
    private long payment_id;

    public long getOrderId(){
        return order_id;
    }

    public long getCustomerId(){
        return customer_id;
    }

    public long getRestaurantId(){
        return restaurant_id;
    }

    public long getMealId(){
        return meal_id;
    }

    public long getPaymentId(){
        return payment_id;
    }


    public void setCustomerId(long customerId){
         customer_id=customerId;
    }

    public void setRestaurantId(long restaurantId){
        restaurant_id=restaurantId;
    }
    public void setMealId(long mealId){
        meal_id=mealId;
    }

    public void setPaymentId(long paymentId){
        payment_id=paymentId;
    }



    public Order(long customer_id, long restaurant_id, long meal_id, long payment_id){
        this.customer_id=customer_id;
        this.restaurant_id=restaurant_id;
        this.meal_id=meal_id;
        this.payment_id=payment_id;
    }

    public Order(){

    }
}
