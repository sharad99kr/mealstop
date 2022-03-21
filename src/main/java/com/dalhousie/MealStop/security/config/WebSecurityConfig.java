package com.dalhousie.MealStop.security.config;

import com.dalhousie.MealStop.security.filter.CustomAuthenticationFilter;
import com.dalhousie.MealStop.security.filter.CustomAuthorizationFilter;
import com.dalhousie.MealStop.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import javax.annotation.Resource;

import static com.dalhousie.MealStop.security.config.WhitelistUrlConstants.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    CustomAuthenticationManager customAuthenticationManager;

    @Override
    public void configure(WebSecurity web) {
        //web.ignoring().antMatchers("/resources/**").antMatchers("/static/**");
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
                .and().formLogin().loginPage("/login").defaultSuccessUrl("/customer/homepage", true)
                .failureUrl("/login-error").permitAll()
                .and().logout().permitAll();



                /*.antMatchers("/login").permitAll()
                .and()
                .formLogin()
                //.loginPage("/login.html")
                .loginProcessingUrl("/perform_login")
                .defaultSuccessUrl("/homepage.html", true)
                .failureUrl("/login.html?error=true")
                //.failureHandler(authenticationFailureHandler())
                .and()
                .logout()
                .logoutUrl("/perform_logout")
                .invalidateHttpSession(true)
                .and()
                .exceptionHandling().accessDeniedPage("/403");*/
        //http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));
        //http.addFilterAfter(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    protected AuthenticationManager authenticationManager() throws Exception
    {
        return customAuthenticationManager;
    }
}
