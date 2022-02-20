package com.dalhousie.MealStop.user.dbmodels;

import java.util.Date;

public interface ILogin {
    String getUserName();

    void setUserName(String username);

    String getPassword();

    void setPassword(String password);

    Date getLastLogin();

    void setLastLogin(Date lastLogin);

    boolean isActive();

    void setIsActive(boolean isActive);

    long getUserId();

    void setUserId(long userId);
}
