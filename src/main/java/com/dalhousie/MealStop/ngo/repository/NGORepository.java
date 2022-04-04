package com.dalhousie.MealStop.ngo.repository;

import com.dalhousie.MealStop.ngo.model.NGO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NGORepository extends JpaRepository<NGO, Long>
{

}
