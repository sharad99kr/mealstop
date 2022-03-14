package com.dalhousie.MealStop.user.service;

import com.dalhousie.MealStop.user.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService
{
    public abstract void addUser(User user);
    public abstract List<User> getAllUser();
    public User getUserByEmail(String email);
}
