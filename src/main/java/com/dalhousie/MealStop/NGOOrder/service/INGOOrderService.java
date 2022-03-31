package com.dalhousie.MealStop.NGOOrder.service;

import com.dalhousie.MealStop.NGOOrder.model.NGOOrder;


import java.util.List;

public interface INGOOrderService {


    public abstract List<NGOOrder> getNGOOrderWithId(long ngoId);
    public abstract void addNGOOrder(NGOOrder ngoOrder);
}
