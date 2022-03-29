package com.dalhousie.MealStop.security;

import com.dalhousie.MealStop.customer.modal.Customer;
import com.dalhousie.MealStop.security.handler.CustomerAuthenticationSuccessHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class CustomAuthenticationSuccessHandlerTests {

    @Mock
    private HttpServletRequest mockRequest;

    @Mock
    private HttpServletResponse mockResponse;

    @Mock
    private Authentication mockAuthentication;

    @InjectMocks
    private CustomerAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
        //Mockito.lenient().when(mockAuthentication.getAuthorities()).thenReturn(authorities);
    }

    //@Test
    void ShouldNotThrowErrorForOnAuthenticationSuccess() throws ServletException, IOException {
        customAuthenticationSuccessHandler.onAuthenticationSuccess(mockRequest, mockResponse, null, mockAuthentication);
    }
}
