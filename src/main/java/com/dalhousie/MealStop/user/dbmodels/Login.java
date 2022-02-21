package com.dalhousie.MealStop.user.dbmodels;

import javax.persistence.*;

import static javax.persistence.FetchType.EAGER;

@Entity
@Table(name = "login")
public class Login implements ILogin {

    @Id
    @Column(name = "username", updatable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "lastlogin")
    private String lastlogin;

    @Column(name = "isactive")
    private int isActive;

    //@Column(name = "user", nullable = false)
    @OneToOne(fetch = EAGER)
    private User user;

    @Override
    public String getUserName() {
        return this.username;
    }

    @Override
    public void setUserName(String username) {
        this.username = username;
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
        return this.lastlogin;
    }

    @Override
    public void setLastLogin(String lastLogin) {
        this.lastlogin = lastLogin;
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
    public IUser getUser() {
        return user;
    }

    @Override
    public void setUser(IUser user) {
        this.user = (User)user;
    }
}
