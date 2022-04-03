package com.dalhousie.MealStop.security;

import com.dalhousie.MealStop.security.handler.CustomerAuthenticationSuccessHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class CustomAuthenticationSuccessHandlerTests {

    @Mock
    private HttpServletRequest mockRequest;

    @Mock
    private HttpServletResponse mockResponse;

    @Mock
    private Authentication mockAuthentication;

    @Mock
    private Collection<GrantedAuthority> mockGrantedAuthorities;

    @InjectMocks
    private CustomerAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
    }

    @Test
    void ShouldThrowErrorForNoAuthorities() {
        assertThrows(IllegalStateException.class, () -> customAuthenticationSuccessHandler.onAuthenticationSuccess(mockRequest, mockResponse, null, mockAuthentication));
    }
}
