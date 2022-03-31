package com.dalhousie.MealStop.ngo.repository;

import com.dalhousie.MealStop.customer.modal.Customer;
import com.dalhousie.MealStop.ngo.modal.NGO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NGORepository extends JpaRepository<NGO, Long>
{

}
