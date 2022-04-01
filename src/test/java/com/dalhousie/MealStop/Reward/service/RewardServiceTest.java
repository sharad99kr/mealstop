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

    }


    @Test
    void getRewardPointsWithRewards() {
//        MockitoAnnotations.initMocks(this);
//        mockCustomerId= Long.valueOf(123);
//        when(rewardService.getRewardPoints(mockCustomerId)).thenReturn( mockRewardPoint);
//        assertEquals(rewardService.getRewardPoints(mockCustomerId),101);

    }
//
//    @Test
//    void addRewardPoints() {
//    }
//
//    @Test
//    void resetRewardPoints() {
//
//        doNothing().
//                doThrow(new RuntimeException())
//                .when(mockRewardRepository).updateRewardsById(mockCustomerId,0);
//        doNothing().
//                doThrow(new RuntimeException())
//                .when(rewardService).updateRewardPoints(mockCustomerId,0);
//        rewardService.updateRewardPoints(mockCustomerId, 0);
//
//        verify(mockRewardRepository,times(1)).updateRewardsById(mockCustomerId,0);
//    }

//    @Test
//    void updateRewardPoints() {
//
//        doNothing().
//                doThrow(new RuntimeException())
//                .when(mockRewardRepository).updateRewardsById(mockCustomerId,10);
//
//
//        rewardService.updateRewardPoints(mockCustomerId, 10);
//        verify(mockRewardRepository,times(1)).updateRewardsById(mockCustomerId,10);
//
//    }

    @Test
    void isRewardPointsRedeemable() {



    }
//
//    @Test
//    void redeemRewardPoints() {
//    }
}