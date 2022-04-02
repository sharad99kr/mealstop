package com.dalhousie.MealStop.orders.controller;

import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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