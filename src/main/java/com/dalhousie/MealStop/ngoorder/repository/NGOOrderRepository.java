package com.dalhousie.MealStop.ngoorder.repository;

import com.dalhousie.MealStop.ngoorder.model.NGOOrder;
import com.dalhousie.MealStop.common.OrderConstants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface NGOOrderRepository extends JpaRepository<NGOOrder,Integer> {

    @Query(value = OrderConstants.ORDERS_BY_NGO_ID,nativeQuery = true)
    @Modifying
    @Transactional
     List<NGOOrder> findByNGOId(long ngoId);
}
