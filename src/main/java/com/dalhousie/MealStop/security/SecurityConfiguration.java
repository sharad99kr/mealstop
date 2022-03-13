package com.dalhousie.MealStop.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@ComponentScan("com.dalhousie.MealShop")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{
    @Autowired
    CustomAuthenticationManager customAuthenticationManager;

    @Override
    public void configure(WebSecurity web) throws Exception
    {
        web.ignoring().antMatchers("/resources/**");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception
    {
        http.authorizeRequests().antMatchers("/", "/**").permitAll()
                .antMatchers("/customer/**").hasRole("CUSTOMER")
                //.antMatchers("/customer/**").permitAll()
                .antMatchers("/restaurant/**").hasRole("RESTAURANT")
                .anyRequest().authenticated().and().formLogin().loginPage("/login").permitAll().and().logout()
                .permitAll();
    }

    protected AuthenticationManager authenticationManager() throws Exception
    {
        return customAuthenticationManager;
    }
}
