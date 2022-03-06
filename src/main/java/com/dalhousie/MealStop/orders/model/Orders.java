package com.dalhousie.MealStop.orders.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="orders")
public class Orders implements IOrders {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="customerid")
    private long customerid;

    @Column(name="restaurantid")
    private long restaurantid;

    @Column(name="mealid")
    private long mealid;

    @Column(name="paymentid")
    private long paymentid;

    @Override
    public long getOrderId(){
        return id;
    }

    @Override
    public long getCustomerId(){
        return customerid;
    }

    @Override
    public long getRestaurantId(){
        return restaurantid;
    }

    @Override
    public long getMealId(){
        return mealid;
    }

    @Override
    public long getPaymentId(){
        return paymentid;
    }

    @Override
    public void setCustomerId(long customerId){
         customerid=customerId;
    }

    @Override
    public void setRestaurantId(long restaurantId){
        restaurantid=restaurantId;
    }

    @Override
    public void setMealId(long mealId){
        mealid=mealId;
    }

    @Override
    public void setPaymentId(long paymentId){
        paymentid=paymentId;
    }



    public Orders(long customer_id, long restaurant_id, long meal_id, long payment_id){
        this.customerid=customer_id;
        this.restaurantid=restaurant_id;
        this.mealid=meal_id;
        this.paymentid=payment_id;
    }



    public Orders(){

    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Order [id=" + id+"]");
        sb.append(", restaurantId=" + restaurantid);
        sb.append(", customerId=" + customerid);
        sb.append(", mealId=" + mealid);
        sb.append(", paymentId=" + paymentid+"]");
        return sb.toString();
    }

}
