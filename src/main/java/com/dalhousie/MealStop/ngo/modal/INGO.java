package com.dalhousie.MealStop.ngo.modal;

public interface INGO {

    long getId();

    void setId(long id);

    String getNGOName();

    void setNGOName(String name);

    String getEmail();

    void setEmail(String email);

    Integer setTotalOrders(int orderCount);

    int getTotalOrders();
}
