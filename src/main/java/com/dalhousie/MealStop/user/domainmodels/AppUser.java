package com.dalhousie.MealStop.user.domainmodels;

import com.dalhousie.MealStop.user.common.UserType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class AppUser implements UserDetails {
    private String user_id;
    private String name;
    private String password;
    private String username;
    private String email;
    private String mobileNumber;
    private String dateOfBirth;
    private boolean locked;
    private boolean enabled;
    private UserType userType;

    public AppUser(String name,
                   String password,
                   String username,
                   String email,
                   String mobileNumber,
                   String dateOfBirth,
                   UserType userType) {
        this.name = name;
        this.password = password;
        this.username = username;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.dateOfBirth = dateOfBirth;
        this.userType = userType;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        var authority = new SimpleGrantedAuthority(userType.name());
        return Collections.singletonList(authority);
    }

    public String getName(){
        return this.name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !enabled;
    }
}
