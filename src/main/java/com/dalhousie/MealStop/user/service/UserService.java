package com.dalhousie.MealStop.user.service;

import com.dalhousie.MealStop.user.model.User;
import java.util.List;

public interface UserService
{
    public abstract void addUser(User user);
    public abstract List<User> getAllUser();
}
