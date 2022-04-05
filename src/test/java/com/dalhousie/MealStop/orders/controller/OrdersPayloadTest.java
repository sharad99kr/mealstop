package com.dalhousie.MealStop.orders.controller;

import com.dalhousie.MealStop.orders.model.OrdersPayload;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrdersPayloadTest {

    @Test
    void getRewardPointsNoRewards() {
        OrdersPayload payload=new OrdersPayload();
        payload.orderId=1;
        payload.amount=1;
        payload.mealName="meal";
        assertThat(payload.orderId).isEqualTo(1);
    }

}