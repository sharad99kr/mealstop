package com.dalhousie.MealStop.reward.model;

import javax.persistence.*;


@Entity
@Table(name="rewards")
public class Rewards implements IRewards{
    @Id
    @Column(name="customerId")
    private long customerId;

    @Column(name="rewordPoint")
    private int rewardPoint;

    @Override
    public long getCustomerId(){
        return customerId;
    }

    @Override
    public void setCustomerId(long customerId){
        this.customerId=customerId;
    }

    @Override
    public int getRewardPoint(){
        return rewardPoint;
    }

    @Override
    public void setRewardPoint(int points){
        this.rewardPoint=points;
    }

    public Rewards(long id, int point){
        customerId=id;
        rewardPoint=point;
    }

    public Rewards(){

    }
}
