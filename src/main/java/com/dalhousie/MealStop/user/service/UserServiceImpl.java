package com.dalhousie.MealStop.user.service;

import com.dalhousie.MealStop.user.model.User;
import com.dalhousie.MealStop.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserRepository userRepository;

    @Override
    public void addUser(User user)
    {
        userRepository.save(user);
    }

    @Override
    public  List<User> getAllUser()
    {
        List<User> usersList = userRepository.findAll();
        return usersList;
    }
}
