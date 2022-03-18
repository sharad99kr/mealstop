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

    public static String getMonthMapping(int monthId){
        String month="";
        switch (monthId){
            case 0 : month = "January";
                break;
            case 1 : month = "February";
                break;
            case 2 : month = "March";
                break;
            case 3 : month = "April";
                break;
            case 4 : month = "May";
                break;
            case 5 : month = "June";
                break;
            case 6 : month = "July";
                break;
            case 7 : month = "August";
                break;
            case 8 : month = "September";
                break;
            case 9 : month = "October";
                break;
            case 10 : month = "November";
                break;
            case 11 : month = "December";
                break;
        }
        return month;
    }
}
