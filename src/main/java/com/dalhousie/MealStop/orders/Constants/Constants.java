package com.dalhousie.MealStop.orders.Constants;

public class Constants {

    public static final int CANCELLED=0;
    public static final int DELIVERED=1;
    public static final int RECEIVED=2;

    public static final String MOST_ORDERED_MEAL_BY_CUSTOMER_FROM_RESTAURANT ="select meal_id from (select meal_id,count(*) as orderCount from orders where customer_id=?1 and restaurant_id=?2 group by meal_id) as newT order by orderCount desc";
    public static final String MOST_ORDERED_MEAL_BY_CUSTOMER="select meal_id from (select meal_id,count(*) as orderCount from orders where customer_id=?1 group by meal_id) as newT order by orderCount desc";
    public static final String MOST_ORDERED_MEAL_FROM_RESTAURANT ="select meal_id from (select meal_id,count(*) as orderCount from orders where restaurant_id=?1 group by meal_id) as newT order by orderCount desc";
    public static final String MONTHLY_SALES_OF_RESTAURANT="select * from orders where restaurant_id=?1 and year(order_date)=?2";
}
