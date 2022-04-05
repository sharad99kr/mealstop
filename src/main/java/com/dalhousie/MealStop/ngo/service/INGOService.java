package com.dalhousie.MealStop.ngo.service;

import com.dalhousie.MealStop.ngo.model.NGO;
import com.dalhousie.MealStop.orders.model.Orders;
import com.dalhousie.MealStop.user.entity.User;

import java.util.List;

public interface INGOService
{
     NGO getNGOById(String id);
     NGO getNGODetailsFromSession();
     void addNGO(User user);
     Long getLoggedInNGOId();
     void sendCancelledOrderNotification(String mealName);
     List<Orders> getNGOOrderHistory();
}
