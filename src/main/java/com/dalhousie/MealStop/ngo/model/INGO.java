package com.dalhousie.MealStop.ngo.model;


    public interface INGO
    {
        long getId();

        void setId(long id);

        String getNGOName();

        void setNGOName(String name);

        String getEmail();

        void setEmail(String email);

        String getAddress();

        void setAddress(String address);

        String getPhoneNumber();

        void setPhoneNumber(String phoneNumber);

        Integer getTotalOrders();

        void setTotalOrders(Integer totalOrders);
    }

