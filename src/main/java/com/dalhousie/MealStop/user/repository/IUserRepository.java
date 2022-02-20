package com.dalhousie.MealStop.user.repository;

import com.dalhousie.MealStop.user.dbmodels.IUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<IUser, Long> {
}
