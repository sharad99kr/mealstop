package com.dalhousie.MealStop.user.repository;

import com.dalhousie.MealStop.user.entity.User;
import com.dalhousie.MealStop.user.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);
    void save(String token);
    VerificationToken findVerificationTokenByUser(User user);
}
