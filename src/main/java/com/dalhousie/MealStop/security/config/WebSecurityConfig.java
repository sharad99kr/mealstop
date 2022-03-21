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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.dalhousie.MealStop.security.config.WhitelistUrlConstants.*;



@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/resources/**", "/static/**");
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers(new String[]{REGISTER_URL, SIGNUP_URL, VERFIY_REGISTRATION_URL, RESEND_VERIFYTOKEN_URL,
                        RESET_PASSWORD_URL, LOGIN_URL, "/api/v1/user"
                }).permitAll()
                .antMatchers("/api/customer/**").hasAuthority("ROLE_CUSTOMER")
                .antMatchers("/api/restaurant/**").hasAuthority("ROLE_RESTAURANT")
                .antMatchers("/api/ngo/**").hasAuthority("ROLE_NGO")
                .and().formLogin().loginPage("/login").defaultSuccessUrl("/api/v1/user", true)
                .failureUrl("/login.html?error=true").permitAll()
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
        http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));
        http.addFilterAfter(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
