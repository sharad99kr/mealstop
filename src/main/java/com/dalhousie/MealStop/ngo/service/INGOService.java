package com.dalhousie.MealStop.ngo.service;

import com.dalhousie.MealStop.ngo.model.NGO;
import com.dalhousie.MealStop.user.entity.User;

public interface INGOService
{
    public NGO getNGOById(String id);
    public NGO getNGODetailsFromSession();
    public void addNGO(User user);
    public Long getLoggedInNGOId();
}
