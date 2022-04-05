package com.dalhousie.MealStop.security;

import com.dalhousie.MealStop.security.config.CustomAuthenticationManager;
import com.dalhousie.MealStop.security.config.WebSecurityConfig;
import com.dalhousie.MealStop.security.handler.CustomerAuthenticationSuccessHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class WebSecurityConfigTests {

    @Mock
    CustomerAuthenticationSuccessHandler customAuthenticationSuccessHandlerMock;

    @Mock
    CustomAuthenticationManager customAuthenticationManagerMock;

    @Mock
    private WebSecurity webSecurityMock;

    @Mock
    private HttpSecurity httpSecurityMock;

    @Mock
    private WebSecurity.IgnoredRequestConfigurer requestConfigurerMock;

    @InjectMocks
    WebSecurityConfig webSecurityConfig;

    @Test
    void ShouldSetupWebSecurityConfigWithAntMatchers(){
        Mockito.when(webSecurityMock.ignoring()).thenReturn(requestConfigurerMock);
        webSecurityConfig.configure(webSecurityMock);
    }

}
