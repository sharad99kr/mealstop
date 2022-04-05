package com.dalhousie.MealStop.common;
public class NGOConstants
{
    public static final String NGO_NOTIFICATION_SUBJECT = "Cancelled meal available for pickup";

    public static String getNgoNotificationContent(String ngoName, String mealName)
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder = stringBuilder.append("Hi "+ngoName);
        stringBuilder = stringBuilder.append("\n"+mealName +" has been cancelled and is available for pickup");
        return stringBuilder.toString();
    }
}