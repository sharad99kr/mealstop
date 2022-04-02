package com.dalhousie.MealStop.ngoorder.service;

import com.dalhousie.MealStop.ngoorder.model.NGOOrder;


import java.util.List;

public interface INGOOrderService {


    public abstract List<NGOOrder> getNGOOrderWithId(long ngoId);
    public abstract void addNGOOrder(NGOOrder ngoOrder);
}
