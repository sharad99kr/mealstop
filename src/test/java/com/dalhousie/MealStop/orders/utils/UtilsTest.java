package com.dalhousie.MealStop.orders.utils;

import com.dalhousie.MealStop.common.OrderConstants;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UtilsTest {

    @Test
    void getRandomNumberUsingInts() {
        assertThat(Utils.getRandomNumberUsingInts(2, 6)).isGreaterThan(1).isLessThan(7);
    }

    @Test
    void getUrls() {
        assertThat(Utils.getUrls()).isNotNull().element(1).isEqualTo(OrderConstants.FOOD_2);
    }

    @Test
    void getOrderStatusMapping() {
        assertThat(Utils.getOrderStatusMapping(1)).isEqualTo("PROCESSED");

    }

    @Test
    void getMonthMapping() {

        assertThat(Utils.getMonthMapping(1)).isEqualTo("February");
    }
}