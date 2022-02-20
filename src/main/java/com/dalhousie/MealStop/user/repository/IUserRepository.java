package com.dalhousie.MealStop.user.repository;

import com.dalhousie.MealStop.user.dbmodels.IUser;
import com.dalhousie.MealStop.user.dbmodels.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
}
