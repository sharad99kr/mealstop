package com.dalhousie.MealStop.orders.Constants;

public class Constants {

    public static final int CANCELLED=0;
    public static final int DELIVERED=1;
    public static final int RECEIVED=2;

    public static final String MOST_ORDERED_MEAL_BY_CUSTOMER_FROM_RESTAURANT ="select meal_id from (select meal_id,count(*) as orderCount from orders where customer_id=?1 and restaurant_id=?2 group by meal_id) as newT order by orderCount desc";
    public static final String MOST_ORDERED_MEAL_BY_CUSTOMER="select meal_id from (select meal_id,count(*) as orderCount from orders where customer_id=?1 group by meal_id) as newT order by orderCount desc";
    public static final String MOST_ORDERED_MEAL_FROM_RESTAURANT ="select meal_id from (select meal_id,count(*) as orderCount from orders where restaurant_id=?1 group by meal_id) as newT order by orderCount desc";
    public static final String MONTHLY_SALES_OF_RESTAURANT="select * from orders where restaurant_id=?1 and year(order_date)=?2";

    public static final String FOOD_1="https://images.pexels.com/photos/1143754/pexels-photo-1143754.jpeg";
    public static final String FOOD_2="https://images.pexels.com/photos/1438672/pexels-photo-1438672.jpeg";
    public static final String FOOD_3="https://images.pexels.com/photos/1860208/pexels-photo-1860208.jpeg";
    public static final String FOOD_4="https://images.pexels.com/photos/262959/pexels-photo-262959.jpeg";
    public static final String FOOD_5="https://images.pexels.com/photos/1279330/pexels-photo-1279330.jpeg";
    public static final String FOOD_6="https://images.pexels.com/photos/196643/pexels-photo-196643.jpeg";
    public static final String FOOD_7="https://images.pexels.com/photos/1410236/pexels-photo-1410236.jpeg";
    public static final String FOOD_8="https://images.pexels.com/photos/1351238/pexels-photo-1351238.jpeg";
    public static final String FOOD_9="https://images.pexels.com/photos/1128678/pexels-photo-1128678.jpeg";


}
