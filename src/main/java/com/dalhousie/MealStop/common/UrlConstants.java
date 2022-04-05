package com.dalhousie.MealStop.common;

public class UrlConstants {
    //Security config resources
    public static final String API_VERSION_1 = "/api/v1";
    public static final String STATIC_RESOURCES_MATCHER = "/resources/**";
    public static final String CUSTOMER_MATCHER = "/customer/**";
    public static final String RESTAURANT_MATCHER = "/restaurant/**";
    public static final String NGO_MATCHER = "/ngo/**";

    //Registration urls
    public static final String HTTP = "https://";
    public static final String PORT_SEPARATOR = ":";
    public static final String SAVE_PASSWORD = "/api/v1/savePassword?token=";

    public static final String REGISTER_URL = "/register";
    public static final String USER_REGISTER_URL = "user/registration";

    public static final String NGO_REGISTER_URL = "/ngoregister";
    public static final String NGO_USER_REGISTER_URL = "user/ngoregistration";

    public static final String FORGOT_PASSWORD_URL = "/forgotPassword";
    public static final String USER_FORGOT_PASSWORD_URL = "user/forgotpassword";
    public static final String USER_CHANGE_PASSWORD_URL = "user/changepassword";

    public static final String VERIFY_REGISTRATION_URL = "/api/v1/verifyRegistration?token=";

    public static final String SAVE_PASSWORD_URL = "/savePassword";
    public static final String SIGNUP_URL = "/signup";
    public static final String VERIFY_REGISTER_URL = "/verifyRegistration";
    public static final String CHANGE_PASSWORD_URL = "/changePassword";

    //Login urls
    public static final String LOGIN_URL = "/login";
    public static final String LOGIN_ERROR = "/login-error";
    public static final String USER_LOGIN = "user/login";
    public static final String USER_LOGIN_ERROR = "user/loginerror";

    //User url
    public static final String USER_URL = "/user";

    //Customer url
    public static final String CUSTOMER_HOMEPAGE_URL = "/customer/homepage";

    //Restaurant url
    public static final String RESTAURANT_HOMEPAGE_URL = "/restaurant/get_restaurant";

    //NGO url
    public static final String NGO_HOMEPAGE_URL = "/ngo/homepage";
}
