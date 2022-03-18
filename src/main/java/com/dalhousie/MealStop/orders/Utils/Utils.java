package com.dalhousie.MealStop.orders.Utils;

import com.dalhousie.MealStop.orders.Constants.Constants;

public class Utils {
    public static String getOrderStatusMapping(int statusId){
        String status="";
        switch (statusId){
            case Constants.CANCELLED:status="CANCELLED";
                break;
            case Constants.DELIVERED:status="DELIVERED";
                break;
            case Constants.RECEIVED:status="RECEIVED";
                break;
        }
        return status;
    }
}
