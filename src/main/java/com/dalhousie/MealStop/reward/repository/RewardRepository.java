package com.dalhousie.MealStop.reward.repository;

import com.dalhousie.MealStop.common.RewardConstants;
import com.dalhousie.MealStop.reward.model.Rewards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface RewardRepository extends JpaRepository<Rewards,Long> {

    Rewards findByCustomerId(Long customerId);

    @Query(value = RewardConstants.UPDATE_REWARD_POINTS,nativeQuery = true)
    @Modifying
    @Transactional
    void updateRewardsById(long customerId, int points);
}
