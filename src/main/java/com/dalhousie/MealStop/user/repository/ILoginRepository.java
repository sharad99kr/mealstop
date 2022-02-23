package com.dalhousie.MealStop.user.repository;

import com.dalhousie.MealStop.user.dbmodels.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface ILoginRepository extends JpaRepository<Login, String>  {

    Optional<Login> findByUsername(String username);

    @Transactional
    @Modifying
    @Query("UPDATE Login l " +
            "SET l.isActive = 1 WHERE l.user = ?1")
    int enableAppUser(long userId);
}
