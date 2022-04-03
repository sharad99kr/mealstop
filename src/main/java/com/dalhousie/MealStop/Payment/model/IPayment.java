package com.dalhousie.MealStop.Payment.model;

public interface IPayment {
    public long getPaymentId();
    public String getPaymentMethod();
    public int getPaymentAmount();

    public void setPaymentMethod(String method);
    public void setPaymentAmount(int amount);

}
