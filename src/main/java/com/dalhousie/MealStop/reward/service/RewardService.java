package com.dalhousie.MealStop.reward.service;

import com.dalhousie.MealStop.common.RewardConstants;
import com.dalhousie.MealStop.reward.model.Rewards;
import com.dalhousie.MealStop.reward.repository.RewardRepository;
import com.dalhousie.MealStop.customer.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RewardService implements IRewardService{

    @Autowired
    private RewardRepository rewardRepository;

    @Autowired
    private ICustomerService customerService;

    @Override
    public int getRewardPoints(long customerId){
        Rewards reward=rewardRepository.findByCustomerId(customerId);
        if(reward==null){
            return RewardConstants.ZERO_POINTS;
        }
        return reward.getRewardPoint();
    }

    @Override
    public void addRewardPoints(long customerId){
        Rewards reward=rewardRepository.findByCustomerId(customerId);
        if(reward==null){
            reward = new Rewards(customerId, RewardConstants.POINTS_AWARDED_PER_MEAL);
            rewardRepository.save(reward);
            return;
        }
        int pointsEarned = reward.getRewardPoint();
        pointsEarned += RewardConstants.POINTS_AWARDED_PER_MEAL;
        updateRewardPoints( customerId, pointsEarned);
    }

    @Override
    public void resetRewardPoints(long customerId){
        updateRewardPoints( customerId, RewardConstants.ZERO_POINTS);
    }

    void updateRewardPoints(long customerId, int points){
        rewardRepository.updateRewardsById(customerId,points);
    }

    public boolean isRewardPointsRedeemable(long customerId){
        return getRewardPoints(customerId)> RewardConstants.MINIMUM_POINTS_TO_REDEEM_TOKEN;
    }

    public int redeemRewardPoints(long customerId){
        int maxtoken=0;

        if(isRewardPointsRedeemable(customerId)){
            int totalPoints=getRewardPoints(customerId);
            maxtoken=totalPoints/ RewardConstants.MINIMUM_POINTS_TO_REDEEM_TOKEN;

            customerService.incrementCustomerToken(maxtoken);
            int remainingPoints=maxtoken% RewardConstants.MINIMUM_POINTS_TO_REDEEM_TOKEN;
            updateRewardPoints(customerId,remainingPoints);
        }
        return maxtoken;
    }
}
