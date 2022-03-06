package com.dalhousie.MealStop.authorizationserver.repository;

import com.dalhousie.MealStop.authorizationserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);
}
