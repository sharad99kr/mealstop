package com.dalhousie.MealStop.Reward.repository;

import com.dalhousie.MealStop.Reward.constants.Constants;
import com.dalhousie.MealStop.Reward.model.Rewards;
import com.dalhousie.MealStop.orders.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface RewardRepository extends JpaRepository<Rewards,Long> {

    public Rewards findByCustomerId(Long customerId);

    @Query(value = Constants.UPDATE_REWARD_POINTS,nativeQuery = true)
    @Modifying
    @Transactional
    public void updateRewardsById(long customerId, int points);
}
