package com.dalhousie.MealStop.ngo.modal;

public interface INGO {

    long getId();

    void setId(long id);

    String getNGOName();

    void setNGOName(String name);

    String getEmail();

    void setEmail(String email);

    String getAddress();

    void setAddress(String address);

    Integer getPhoneNumber(int orderCount);

    int setPhoneNumber();

    Integer setTotalOrders(int orderCount);

    int getTotalOrders();
}
