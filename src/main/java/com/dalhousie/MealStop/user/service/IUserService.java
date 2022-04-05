package com.dalhousie.MealStop.user.service;

import com.dalhousie.MealStop.user.entity.User;
import com.dalhousie.MealStop.user.models.UserModel;
import java.util.List;
import java.util.Optional;

public interface IUserService {
    User signUpUser(UserModel user);

    User findUserByEmail(String email);

    User saveUser(User user);

    List<User> getUsers();

    void saveVerificationTokenForUser(User user, String token);

    String validateVerificationToken(String token);

    void createPasswordResetTokenForUser(User user, String token);

    String validatePasswordResetToken(String token);

    Optional<User> getUserByPasswordResetToken(String token);

    void changePassword(User user, String newPassword);

    boolean checkIfValidOldPassword(User user, String oldPassword);
}
