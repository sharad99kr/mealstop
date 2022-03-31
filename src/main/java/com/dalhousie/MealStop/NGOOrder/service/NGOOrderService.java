package com.dalhousie.MealStop.NGOOrder.service;

import com.dalhousie.MealStop.NGOOrder.model.NGOOrder;
import com.dalhousie.MealStop.NGOOrder.repository.NGOOrderRepository;
import com.dalhousie.MealStop.orders.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;



@Service
public class NGOOrderService implements INGOOrderService {

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
