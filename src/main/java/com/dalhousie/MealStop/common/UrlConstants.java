package com.dalhousie.MealStop.common;

public class UrlConstants {
    //Security config resources
    public static final String API_VERSION_1 = "/api/v1";
    public static final String STATIC_RESOURCES_MATCHER = "/resources/**";
    public static final String CUSTOMER_MATCHER = "/customer/**";
    public static final String RESTAURANT_MATCHER = "/restaurant/**";
    public static final String NGO_MATCHER = "/ngo/**";


    public static final String HTTP = "http://";
    public static final String PORT_SEPARATOR = ":";
    public static final String SAVE_PASSWORD = "/api/v1/savePassword?token=";

    public static final String REGISTER_URL = "/register";
    public static final String USER_REGISTER_URL = "user/registration";

    public static final String NGO_REGISTER_URL = "/ngoregister";
    public static final String NGO_USER_REGISTER_URL = "user/ngoregistration";

    public static final String FORGOT_PASSWORD_URL = "/forgotpassword";
    public static final String USER_FORGOT_PASSWORD_URL = "user/forgotpassword";


    public static final String RESTAURANT_REGISTER_URL = "/api/v1/restaurantregister";
    public static final String SIGNUP_URL = "/api/v1/signup";
    public static final String VERFIY_REGISTRATION_URL = "/api/v1/verifyRegistration";
    public static final String RESEND_VERIFYTOKEN_URL = "/api/v1/resendVerifyToken";
    public static final String RESET_PASSWORD_URL = "/api/v1/forgotPassword";
    public static final String SAVE_PASSWORD_URL = "/api/v1/savePassword";

    public static final String VERIFY_REGISTRATION_URL = "/api/v1/verifyRegistration?token=";

    public static final String LOGIN_URL = "/login";
    public static final String LOGIN_ERROR = "/login-error";
    public static final String USER_LOGIN = "user/login";
    public static final String USER_LOGIN_ERROR = "user/loginerror";

    public static final String USER_URL = "/user";
}
