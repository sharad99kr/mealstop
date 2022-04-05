package com.dalhousie.MealStop.reward.service;

import com.dalhousie.MealStop.common.OrderConstants;
import com.dalhousie.MealStop.orders.repository.OrderRepository;
import com.dalhousie.MealStop.reward.model.Rewards;
import com.dalhousie.MealStop.reward.repository.RewardRepository;
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
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.client.ExpectedCount.times;


import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

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
    @InjectMocks
    private static RewardService rewardService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockCustomerId=1L;
        mockRewards=new Rewards();
        mockRewards.setRewardPoint(101);
        mockRewards.setCustomerId(Long.valueOf(123));
        mockRewards2=new Rewards(22,33);
        mockRewardPoint=101;

    }

    @Test
    void getRewardPoints(){
        mockCustomerId=1;
        mockRewards=new Rewards(mockCustomerId,2);

        Mockito.lenient().when(mockRewardRepository.findByCustomerId(mockCustomerId)).thenReturn(mockRewards);
        assertThat(rewardService.getRewardPoints(mockCustomerId)).isEqualTo(2);
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


//    @Test
//    void resetRewardPoints() {
//
//        RewardRepository obj=Mockito.spy(rewardService);
//        verify(mockRewardRepository, Mockito.times(1)).resetRewardPoints(mockCustomerId,100);
//
//    }
//
//    @Test
//    void updateRewardPoints() {
//
//        RewardRepository obj=Mockito.spy(mockRewardRepository);
//        verify(mockRewardRepository, Mockito.times(1)).updateRewardsById(mockCustomerId,100);
//
//    }

    @Test
    void isRewardPointsRedeemable() {

        Mockito.lenient().when(mockRewardRepository.findByCustomerId(mockCustomerId)).thenReturn(mockRewards);
        assertThat(rewardService.isRewardPointsRedeemable(mockCustomerId)).isEqualTo(true);

    }

    @Test
    void redeemRewardPoints() {
    }
}