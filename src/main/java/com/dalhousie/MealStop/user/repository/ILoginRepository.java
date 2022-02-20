package com.dalhousie.MealStop.user.repository;

import com.dalhousie.MealStop.user.dbmodels.ILogin;
import com.dalhousie.MealStop.user.dbmodels.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILoginRepository extends JpaRepository<Login, String>  {
}
