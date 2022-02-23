package com.dalhousie.MealStop.user.repository;

import com.dalhousie.MealStop.user.dbmodels.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface IUserRepository extends JpaRepository<User, Long> {
    //Optional<User> findByUserid(Long userid);
    Optional<User> findByEmail(String email);

}
