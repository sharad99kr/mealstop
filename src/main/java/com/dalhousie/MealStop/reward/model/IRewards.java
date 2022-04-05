package com.dalhousie.MealStop.reward.model;

public interface IRewards {

     long getCustomerId();

     void setCustomerId(long customerId);

     int getRewardPoint();

     void setRewardPoint(int points);
}
