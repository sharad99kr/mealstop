package com.dalhousie.MealStop.user.repository;

import com.dalhousie.MealStop.user.dbmodels.ILogin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ILoginRepository extends JpaRepository<ILogin, String> {
}
