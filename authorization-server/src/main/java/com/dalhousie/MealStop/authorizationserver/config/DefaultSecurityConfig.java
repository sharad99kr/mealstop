package com.dalhousie.MealStop.authorizationserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class DefaultSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity security) throws Exception {
        security.authorizeRequests( request -> request.anyRequest().authenticated())
                .formLogin(Customizer.withDefaults());
        return security.build();
    }
}
