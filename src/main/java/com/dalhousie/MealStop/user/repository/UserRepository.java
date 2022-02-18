package com.dalhousie.MealStop.user.repository;

import com.dalhousie.MealStop.user.model.User;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
    List<User> findByFirstName(String firstName);
    List<User> findByLastName(String lastName);
}
