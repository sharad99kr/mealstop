package com.dalhousie.MealStop.ngoorder.service;

import com.dalhousie.MealStop.ngoorder.model.NGOOrder;
import com.dalhousie.MealStop.ngoorder.repository.NGOOrderRepository;
import com.dalhousie.MealStop.orders.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;



@Service
public class NGOOrderServiceImpl implements INGOOrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private NGOOrderRepository ngoOrderRepository;


    @Override
    public void addNGOOrder(NGOOrder ngoOrder){
        //this method adds new order that has been placed
        ngoOrderRepository.save(ngoOrder);

    }

    @Override
    public List<NGOOrder> getNGOOrderWithId(long ngoId){

        return ngoOrderRepository.findByNGOId (ngoId);
    }

}
