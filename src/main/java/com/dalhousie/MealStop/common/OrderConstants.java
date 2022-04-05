package com.dalhousie.MealStop.common;

public class OrderConstants {

    //food order status
    public static final int CANCELLED=0;
    public static final int PROCESSED=1;
    public static final int DELIVERED=2;
    public static final int ACTIVE=3;
    public static final int CLAIMED=4;

    public static final String MONTHLY_SALES_OF_RESTAURANT="select * from orders where restaurant_id=?1 and year(order_date)=?2";

    public static final String ORDERS_BY_NGO_ID="select * from `ngoorder` where ngo_id=?1";

    //static image urls
    public static final String FOOD_1="https://images.pexels.com/photos/1143754/pexels-photo-1143754.jpeg";
    public static final String FOOD_2="https://images.pexels.com/photos/1438672/pexels-photo-1438672.jpeg";
    public static final String FOOD_3="https://images.pexels.com/photos/1860208/pexels-photo-1860208.jpeg";
    public static final String FOOD_4="https://images.pexels.com/photos/262959/pexels-photo-262959.jpeg";
    public static final String FOOD_5="https://images.pexels.com/photos/1279330/pexels-photo-1279330.jpeg";
    public static final String FOOD_6="https://images.pexels.com/photos/196643/pexels-photo-196643.jpeg";
    public static final String FOOD_7="https://images.pexels.com/photos/1410236/pexels-photo-1410236.jpeg";
    public static final String FOOD_8="https://images.pexels.com/photos/1351238/pexels-photo-1351238.jpeg";
    public static final String FOOD_9="https://images.pexels.com/photos/1128678/pexels-photo-1128678.jpeg";

    //csv constants
    public static final String FILE_WRITE_ERROR="Error While writing CSV ";
    public static final String MONTHLY_REPORT="Monthly report for year %d";
    public static final String MONTH_HEADER="Month";
    public static final String EARNINGS_HEADER="Earnings($)";

    public static final String ADD_ORDER="orders/add_order";
    public static final String GET_PROCESSED_ORDER="orders/customer_processed_orders";
    public static final String GET_ALL_ORDER="orders/customer_orders_all";
    public static final String GET_CANCELLED_ORDER="orders/cancelled_orders";
    public static final String GET_ORDER_BY_RESTAURANT_ID="orders/restaurant_orders/{id}";
    public static final String RESTAURANT_REDIRECTION_URL="redirect:/orders/restaurant_orders/";


    //months
    public static final int JAN=0;
    public static final int FEB=1;
    public static final int MARCH=2;
    public static final int APRIL=3;
    public static final int MAY=4;
    public static final int JUNE=5;
    public static final int JULY=6;
    public static final int AUG=7;
    public static final int SEP=8;
    public static final int OCT=9;
    public static final int NOV=10;
    public static final int DEC=11;


}
