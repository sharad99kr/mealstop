package com.dalhousie.MealStop.reward.service;

public interface IRewardService {
    void addRewardPoints(long customerId);

    void resetRewardPoints(long customerId);

    int getRewardPoints(long customerId);

    boolean isRewardPointsRedeemable(long customerId);

    int redeemRewardPoints(long customerId);
}
