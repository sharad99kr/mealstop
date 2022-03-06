package com.dalhousie.MealStop.Payment.model;

import javax.persistence.*;

@Entity
@Table(name="payment")
public class Payment implements IPayment{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="method")
    private String method;

    @Column(name="amount")
    private int amount;

    @Override
    public long getPaymentId(){
        return id;
    }

    @Override
    public String getPaymentMethod(){
        return method;
    }

    @Override
    public int getPaymentAmount(){
        return amount;
    }

    @Override
    public void setPaymentMethod(String method){
        this.method=method;
    }

    @Override
    public void setPaymentAmount(int amount){
        this.amount=amount;
    }

    public Payment(int paymentAmount, String paymentMethod){
        this.amount=paymentAmount;
        this.method=paymentMethod;
    }

    public Payment(){

    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Payment [id=" + id+"]");
        sb.append(", paymentMethod=" + method);
        sb.append(", paymentAmount=" + amount+"]");
        return sb.toString();
    }

}
