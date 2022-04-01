package com.dalhousie.MealStop.orders.model;

import com.dalhousie.MealStop.meal.model.Meal;
import com.dalhousie.MealStop.restaurant.model.Restaurant;
import com.dalhousie.MealStop.customer.modal.Customer;

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

    @Column(name="paymentId")
    private long paymentId;

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
    public long getPaymentId(){
        return paymentId;
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

    @Override
    public void setPaymentId(long paymentId){
        this.paymentId=paymentId;
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



    public Orders(long customer_id, long restaurant_id, long meal_id, long payment_id, long amount, int status){
        this.customerId=customer_id;
        this.restaurantId=restaurant_id;
        this.mealId=meal_id;
        this.paymentId=payment_id;
        this.amount=amount;
        this.status=status;
        setOrderTime();
        this.date=getOrderTime();

    }



    public Orders(){

    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Order [id=" + id);
        sb.append(", restaurantId=" + restaurantId);
        sb.append(", customerId=" + customerId);
        sb.append(", mealId=" + mealId);
        sb.append(", paymentId=" + paymentId+"]");

        return sb.toString();
    }

}






