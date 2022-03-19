package com.dalhousie.MealStop.Reward.service;

public interface IRewardService {

    //this method definition adds rewards points based on the meal ordered.
    // For each meal ordered 10 reward points will be added to the
    // customers account.
    public abstract void addRewardPoints(long customerId);

    //this method definition resets rewards points if user has already redeemed the reward points
    public abstract void resetRewardPoints(long customerId);

    //this method definition returns total reward points that user has accumulated so far. This information
    // can be used to enable redeem points
    public abstract int getRewardPoints(long customerId);

    //this method definition checks rewards points and returns boolean state whether reward points
    // can be redeemed or not
    public abstract boolean isRewardPointsRedeemable(long customerId);


    //this method definition redeems reward points and return number of tokens that has been
    // claimed using reward points
    public abstract int redeemRewardPoints(long customerId);


}
