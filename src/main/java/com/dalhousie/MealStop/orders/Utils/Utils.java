package com.dalhousie.MealStop.orders.Utils;

import com.dalhousie.MealStop.common.OrderConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Utils {

    public static int getRandomNumberUsingInts(int min, int max) {
        Random random = new Random();
        return random.ints(min, max)
                .findFirst()
                .getAsInt();
    }

    public static List<String> getUrls(){
        List<String> urlList=new ArrayList<>();
        urlList.add(OrderConstants.FOOD_1);
        urlList.add(OrderConstants.FOOD_2);
        urlList.add(OrderConstants.FOOD_3);
        urlList.add(OrderConstants.FOOD_4);
        urlList.add(OrderConstants.FOOD_5);
        urlList.add(OrderConstants.FOOD_6);
        urlList.add(OrderConstants.FOOD_7);
        urlList.add(OrderConstants.FOOD_8);
        urlList.add(OrderConstants.FOOD_9);
        return urlList;
    }

    public static String getOrderStatusMapping(int statusId){
        String status="";
        switch (statusId){
            case OrderConstants.CANCELLED:status="CANCELLED";
                break;
            case OrderConstants.DELIVERED:status="DELIVERED";
                break;
            case OrderConstants.PROCESSED:status="PROCESSED";
                break;
            case OrderConstants.ACTIVE:status="ACTIVE";
                break;
        }
        return status;
    }

    public static String getMonthMapping(int monthId){
        String month="";
        switch (monthId){
            case OrderConstants.JAN : month = "January";
                break;
            case OrderConstants.FEB : month = "February";
                break;
            case OrderConstants.MARCH : month = "March";
                break;
            case OrderConstants.APRIL : month = "April";
                break;
            case OrderConstants.MAY : month = "May";
                break;
            case OrderConstants.JUNE : month = "June";
                break;
            case OrderConstants.JULY : month = "July";
                break;
            case OrderConstants.AUG : month = "August";
                break;
            case OrderConstants.SEP: month = "September";
                break;
            case OrderConstants.OCT : month = "October";
                break;
            case OrderConstants.NOV: month = "November";
                break;
            case OrderConstants.DEC: month = "December";
                break;
        }
        return month;
    }
}
