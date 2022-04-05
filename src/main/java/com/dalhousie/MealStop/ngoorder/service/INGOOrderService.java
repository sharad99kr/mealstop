package com.dalhousie.MealStop.ngoorder.service;

import com.dalhousie.MealStop.ngoorder.model.NGOOrder;


import java.util.List;

public interface INGOOrderService {
     List<NGOOrder> getNGOOrderWithId(long ngoId);
     void addNGOOrder(NGOOrder ngoOrder);
}
