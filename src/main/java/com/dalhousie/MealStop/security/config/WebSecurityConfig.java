package com.dalhousie.MealStop.security.config;

import com.dalhousie.MealStop.common.RoleEnum;
import com.dalhousie.MealStop.security.handler.CustomerAuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static com.dalhousie.MealStop.common.UrlConstants.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CustomerAuthenticationSuccessHandler customAuthHandler;

    @Autowired
    CustomAuthenticationManager customAuthManager;

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(STATIC_RESOURCES_MATCHER);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers(CUSTOMER_MATCHER).hasAuthority(String.valueOf(RoleEnum.ROLE_CUSTOMER))
                .antMatchers(RESTAURANT_MATCHER).hasAuthority(String.valueOf(RoleEnum.ROLE_RESTAURANT))
                .antMatchers(NGO_MATCHER).hasAuthority(String.valueOf(RoleEnum.ROLE_NGO))
                .and().formLogin().loginPage(LOGIN_URL).successHandler(customAuthHandler)
                .failureUrl(LOGIN_ERROR).permitAll()
                .and().logout().permitAll();
    }

    protected AuthenticationManager authenticationManager() {
        return customAuthManager;
    }
}
