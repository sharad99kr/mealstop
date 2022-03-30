package com.dalhousie.MealStop.ngo.modal;


import com.dalhousie.MealStop.domainconstants.MealStopConstants;
import com.dalhousie.MealStop.user.entity.User;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;

@Entity
@Table(name = "ngo")
public class NGO implements INGO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

}