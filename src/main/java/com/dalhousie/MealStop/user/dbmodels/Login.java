package com.dalhousie.MealStop.user.dbmodels;

import java.util.Date;

public class Login implements ILogin {
    private String userName;
    private String password;
    private Date lastLogin;
    private boolean isActive;
    private long userId;

    @Override
    public String getUserName() {
        return this.userName;
    }

    @Override
    public void setUserName(String username) {
        this.userName = username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Date getLastLogin() {
        return this.lastLogin;
    }

    @Override
    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public long getUserId() {
        return 0;
    }

    @Override
    public void setUserId(long userId){
        this.userId = userId;
    }
}
