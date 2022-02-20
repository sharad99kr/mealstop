package com.dalhousie.MealStop.user.dbmodels;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "login")
public class Login implements ILogin {

    @Id
    @Column(name = "username", updatable = false)
    private String userName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "lastLogin")
    private String lastLogin;

    @Column(name = "isactive")
    private int isActive;

    @Column(name = "userid", nullable = false)
    private long userid;

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
    public String getLastLogin() {
        return this.lastLogin;
    }

    @Override
    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void setIsActive(boolean isActive) {
        this.isActive = isActive == true ? 1 : 0;
    }

    @Override
    public long getUserId() {
        return 0;
    }

    @Override
    public void setUserId(long userId) {
        this.userid = userId;
    }
}
