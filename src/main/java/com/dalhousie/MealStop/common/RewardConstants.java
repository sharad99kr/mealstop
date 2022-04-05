package com.dalhousie.MealStop.common;

public class RewardConstants {
    public static final int ZERO_POINTS=0;

    public static final int POINTS_AWARDED_PER_MEAL=10;

    public static final int MINIMUM_POINTS_TO_REDEEM_TOKEN=50;

    public static final String UPDATE_REWARD_POINTS ="UPDATE rewards SET reword_point = ?2 where customer_id=?1";
}