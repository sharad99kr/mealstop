package com.dalhousie.MealStop.orders.model;

import com.dalhousie.MealStop.Meal.model.Meal;
import com.dalhousie.MealStop.Restaurant.model.Restaurant;
import com.dalhousie.MealStop.customer.modal.Customer;

import javax.persistence.*;


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


    public Orders(long customer_id, long restaurant_id, long meal_id, long payment_id){
        this.customerId=customer_id;
        this.restaurantId=restaurant_id;
        this.mealId=meal_id;
        this.paymentId=payment_id;
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
