package com.dalhousie.MealStop.security;

import com.dalhousie.MealStop.security.config.CustomAuthenticationManager;
import com.dalhousie.MealStop.user.entity.User;
import com.dalhousie.MealStop.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class CustomAuthenticationManagerTests {

    @Mock
    private UserService mockUserService;

    @Mock
    private BCryptPasswordEncoder encode;

    @Mock
    private User mockUser;

    @Mock
    private Authentication mockAuthentication;

    @InjectMocks
    private CustomAuthenticationManager customAuthenticationManager;

    private String rawPassword;
    private String wrongRawPassword;
    private String hashPassword;
    private String email;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        rawPassword = "password";
        wrongRawPassword = "password1";
        hashPassword = "password";
        email = "abc@abc.com";

        Mockito.lenient().when(mockAuthentication.getPrincipal()).thenReturn(email);
        Mockito.lenient().when(mockAuthentication.getCredentials()).thenReturn(rawPassword);
    }

    @Test
    void ShouldReturnBadCredentialsWhenUserNotFound() {
        Mockito.lenient().when(mockUserService.findUserByEmail(any(String.class))).thenReturn(null);
        assertThrows(BadCredentialsException.class, () -> customAuthenticationManager.authenticate(mockAuthentication));
    }

    @Test
    void ShouldReturnBadCredentialsWhenPasswordDoesNotMatch() {
        User user = new User();
        user.setRole("Customer");
        user.setEnabled(true);
        user.setPassword(hashPassword);
        Mockito.lenient().when(mockUser.isEnabled()).thenReturn(false);
        assertThrows(BadCredentialsException.class, () -> customAuthenticationManager.authenticate(mockAuthentication));

    }

    @Test
    void ShouldReturnNullWhenUserNotEnabled() {
        Mockito.lenient().when(mockUser.isEnabled()).thenReturn(false);
        Mockito.lenient().when(encode.matches(any(String.class), any(String.class))).thenReturn(true);
        assertThrows(BadCredentialsException.class, () -> customAuthenticationManager.authenticate(mockAuthentication));
    }

    @Test
    void ShouldWorkWhenUserEnabled() {
        User user = new User();
        user.setRole("Customer");
        user.setEnabled(true);
        user.setPassword(hashPassword);
        Mockito.lenient().when(mockUser.isEnabled()).thenReturn(true);
        Mockito.lenient().when(encode.matches(any(String.class), any(String.class))).thenReturn(true);
        Mockito.lenient().when(mockUserService.findUserByEmail(any(String.class))).thenReturn(user);
        assertDoesNotThrow(() -> customAuthenticationManager.authenticate(mockAuthentication));
    }

}
