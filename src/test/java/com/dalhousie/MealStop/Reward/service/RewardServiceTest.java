package com.dalhousie.MealStop.Reward.service;

import com.dalhousie.MealStop.Reward.model.Rewards;
import com.dalhousie.MealStop.Reward.repository.RewardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class RewardServiceTest {

    @Mock
    private RewardRepository mockRewardRepository;

    @Mock
    private static IRewardService rewardService;


    private Long mockCustomerId;

    @Mock
    private Rewards mockRewards;


    private int mockRewardPoint;


    @BeforeEach
    public void setup() {

        mockRewards=new Rewards(Long.valueOf(123),101);
        mockRewardPoint=101;
        MockitoAnnotations.initMocks(this);
        rewardService=new RewardService();

    }


    @Test
    void getRewardPointsNoRewards() {
        MockitoAnnotations.initMocks(this);
        assertEquals(rewardService.getRewardPoints(mockCustomerId),0);
    }

    @Test
    void getRewardPointsWithRewards() {
        MockitoAnnotations.initMocks(this);
        mockCustomerId= Long.valueOf(123);
        when(rewardService.getRewardPoints(mockCustomerId)).thenReturn( mockRewardPoint);
        assertEquals(rewardService.getRewardPoints(mockCustomerId),101);

    }
//
//    @Test
//    void addRewardPoints() {
//    }
//
//    @Test
//    void resetRewardPoints() {
//    }
//
//    @Test
//    void updateRewardPoints() {
//    }
//
//    @Test
//    void isRewardPointsRedeemable() {
//    }
//
//    @Test
//    void redeemRewardPoints() {
//    }
}