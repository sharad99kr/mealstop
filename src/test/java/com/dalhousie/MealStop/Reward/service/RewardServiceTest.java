package com.dalhousie.MealStop.Reward.service;

import com.dalhousie.MealStop.Reward.constants.Constants;
import com.dalhousie.MealStop.Reward.model.Rewards;
import com.dalhousie.MealStop.Reward.repository.RewardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


import static org.mockito.Mockito.doNothing;
import org.mockito.internal.stubbing.answers.DoesNothing;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class RewardServiceTest {

    @Mock
    private RewardRepository mockRewardRepository;

    @Mock
    private Rewards mockRewards;

    @Mock
    private Rewards mockRewards2;

    private int mockRewardPoint;
    private long mockCustomerId;


    @Autowired
    @Mock
    private static RewardService rewardService;

    @BeforeEach
    public void setup() {
        mockCustomerId=1L;
        mockRewards=new Rewards();
        mockRewards.setRewardPoint(101);
        mockRewards.setCustomerId(Long.valueOf(123));

        mockRewards2=new Rewards(22,33);

        mockRewardPoint=101;
        //MockitoAnnotations.initMocks(this);
    }


    @Test
    void getRewardPointsNoRewards() {
        MockitoAnnotations.initMocks(this);
        assertEquals(rewardService.getRewardPoints(mockCustomerId),0);
    }

    @Test
    public void getterSetterTest(){
        assertThat(mockRewards.getRewardPoint()).isEqualTo(101);
        assertThat(mockRewards.getCustomerId()).isEqualTo(123L);
        assertThat(mockRewards2.getRewardPoint()).isEqualTo(33);
        assertThat(mockRewards2.getCustomerId()).isEqualTo(22L);

    }

//
//    @Test
//    void getRewardPointsWithRewards() {
//
//    }
//
//    @Test
//    void addRewardPoints() {
//    }
//
//    @Test
//    void resetRewardPoints() {
//
//
//    }
//
//    @Test
//    void updateRewardPoints() {
//
//    }
//
//    @Test
//    void isRewardPointsRedeemable() {
//
//
//
//    }
//
//    @Test
//    void redeemRewardPoints() {
//    }
}