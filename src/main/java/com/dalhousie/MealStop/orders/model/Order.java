package com.dalhousie.MealStop.orders.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


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

    @Override
    public long getOrderId(){
        return order_id;
    }

    @Override
    public long getCustomerId(){
        return customer_id;
    }

    @Override
    public long getRestaurantId(){
        return restaurant_id;
    }

    @Override
    public long getMealId(){
        return meal_id;
    }

    @Override
    public long getPaymentId(){
        return payment_id;
    }

    @Override
    public void setCustomerId(long customerId){
         customer_id=customerId;
    }

    @Override
    public void setRestaurantId(long restaurantId){
        restaurant_id=restaurantId;
    }

    @Override
    public void setMealId(long mealId){
        meal_id=mealId;
    }

    @Override
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

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Order [id=" + order_id);
        sb.append(", restaurantId=" + restaurant_id);
        sb.append(", customerId=" + customer_id);
        sb.append(", mealId=" + meal_id);
        sb.append(", paymentId=" + payment_id+"]");
        return sb.toString();
    }

}
