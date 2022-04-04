package com.dalhousie.MealStop.user.repository;

import com.dalhousie.MealStop.user.entity.User;
import com.dalhousie.MealStop.user.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String email);
}
