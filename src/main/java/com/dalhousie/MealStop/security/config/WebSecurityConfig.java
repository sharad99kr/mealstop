package com.dalhousie.MealStop.security.config;

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
    CustomerAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired
    CustomAuthenticationManager customAuthenticationManager;

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/resources/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers(new String[]{REGISTER_URL, SIGNUP_URL, VERFIY_REGISTRATION_URL, RESEND_VERIFYTOKEN_URL,
                        RESET_PASSWORD_URL, LOGIN_URL, "/api/v1/user"
                }).permitAll()
                .antMatchers("/customer/**").hasAuthority("ROLE_CUSTOMER")
                .antMatchers("/restaurant/**").hasAuthority("ROLE_RESTAURANT")
                .antMatchers("/ngo/**").hasAuthority("ROLE_NGO")
                .and().formLogin().loginPage("/login").successHandler(customAuthenticationSuccessHandler)
                .failureUrl("/login-error").permitAll()
                .and().logout().permitAll();
    }

    protected AuthenticationManager authenticationManager() throws Exception
    {
        return customAuthenticationManager;
    }
}
