package com.dalhousie.MealStop.Reward.service;

import com.dalhousie.MealStop.Reward.model.Rewards;
import com.dalhousie.MealStop.Reward.repository.RewardRepository;
import com.dalhousie.MealStop.orders.Constants.Constants;
import com.dalhousie.MealStop.orders.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.naming.NoPermissionException;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class RewardServiceTest {

    @Mock
    private RewardRepository mockRewardRepository;

    @Mock
    private long mockCustomerId;

    @Mock
    private Rewards mockRewards;

    @Mock
    private int mockRewardPoint;


    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);

        Mockito.when(mockRewardRepository.findByCustomerId(mockCustomerId)).thenReturn(mockRewards);
        Mockito.doThrow(new NoPermissionException()).when(mockRewardRepository).updateRewardsById(mockCustomerId,mockRewardPoint);

    }


    @Test
    void getRewardPoints() {
    }

    @Test
    void addRewardPoints() {
    }

    @Test
    void resetRewardPoints() {
    }

    @Test
    void updateRewardPoints() {
    }

    @Test
    void isRewardPointsRedeemable() {
    }

    @Test
    void redeemRewardPoints() {
    }
}