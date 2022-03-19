package com.dalhousie.MealStop.Reward.service;

import com.dalhousie.MealStop.Reward.constants.Constants;
import com.dalhousie.MealStop.Reward.model.Rewards;
import com.dalhousie.MealStop.Reward.repository.RewardRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class RewardService implements IRewardService{

    @Autowired
    private RewardRepository rewardRepository;

    @Override
    public int getRewardPoints(long customerId){
        Rewards reward=rewardRepository.findByCustomerId(customerId);
        if(reward==null){
            return Constants.ZERO_POINTS;
        }
        return reward.getRewardPoint();
    }

    @Override
    public void addRewardPoints(long customerId){

        //this method adds rewards points based on the meal ordered.
        // For each meal ordered 10 reward points will be added to the
        // customers account.
        Rewards reward=rewardRepository.findByCustomerId(customerId);
        if(reward==null){
            //if reward is null, this is new insertion, add default points for first reward
            reward = new Rewards(customerId,Constants.POINTS_AWARDED_PER_MEAL);
            rewardRepository.save(reward);
            return;
        }
        //user has existing rewards.Update total reward points by adding default reward points
        int pointsEarned = reward.getRewardPoint();
        pointsEarned += Constants.POINTS_AWARDED_PER_MEAL;
        updateRewardPoints( customerId, pointsEarned);
    }

    @Override
    public void resetRewardPoints(long customerId){
        //this method definition resets rewards points to zero if user has already redeemed the reward points
        updateRewardPoints( customerId, Constants.ZERO_POINTS);

    }

    void updateRewardPoints(long customerId, int points){

        //this method updates rewards points into database that has been sent for update
        rewardRepository.updateRewardsById(customerId,points);
    }

    //this method implementation checks rewards points and returns boolean state whether reward points
    // can be redeemed or not
    public boolean isRewardPointsRedeemable(long customerId){
        return getRewardPoints(customerId)>Constants.MINIMUM_POINTS_TO_REDEEM_TOKEN;
    }

    //This method implementation puts the logic of redeeming the points to generate
    // tokens that can be claimed by the users. We are providing 1 token per 100 points
    //collected by the user
    public int redeemRewardPoints(long customerId){
        int maxtoken=0;

        //check if user has enough points to claim rewards
        if(isRewardPointsRedeemable(customerId)){
            //get total points collected so far
            int totalPoints=getRewardPoints(customerId);
            //get total number of tokens that can be claimed
            maxtoken=totalPoints/Constants.MINIMUM_POINTS_TO_REDEEM_TOKEN;

            //calculate remaining points that user will have after claiming the rewards
            int remainingPoints=maxtoken%Constants.MINIMUM_POINTS_TO_REDEEM_TOKEN;
            //update the reward points after deducting the points needed to claim tokens
            updateRewardPoints(customerId,remainingPoints);
        }

        return maxtoken;
    }
}
