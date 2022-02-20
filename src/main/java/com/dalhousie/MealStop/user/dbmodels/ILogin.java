package com.dalhousie.MealStop.user.dbmodels;

public interface ILogin {
    String getUserName();

    void setUserName(String username);

    String getPassword();

    void setPassword(String password);

    String getLastLogin();

    void setLastLogin(String lastLogin);

    boolean isActive();

    void setIsActive(boolean isActive);

    long getUserId();

    void setUserId(long userId);
}
