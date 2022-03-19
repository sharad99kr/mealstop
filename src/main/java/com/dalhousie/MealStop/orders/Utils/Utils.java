package com.dalhousie.MealStop.orders.Utils;

import com.dalhousie.MealStop.orders.Constants.Constants;

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
        Collections.addAll(urlList, Constants.FOOD_1, Constants.FOOD_2, Constants.FOOD_3, Constants.FOOD_4, Constants.FOOD_5,
                Constants.FOOD_6,Constants.FOOD_7,Constants.FOOD_8,Constants.FOOD_9);
        return urlList;
    }

    public static String getOrderStatusMapping(int statusId){
        String status="";
        switch (statusId){
            case Constants.CANCELLED:status="CANCELLED";
                break;
            case Constants.DELIVERED:status="DELIVERED";
                break;
            case Constants.PROCESSED:status="PROCESSED";
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
