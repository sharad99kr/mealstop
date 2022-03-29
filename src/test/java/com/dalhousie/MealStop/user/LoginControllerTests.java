package com.dalhousie.MealStop.user;

import com.dalhousie.MealStop.user.controller.LoginController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.dalhousie.MealStop.common.UrlConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class LoginControllerTests {

    @InjectMocks
    private LoginController controller;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void ShouldReturnLoginPage() {
        assertEquals(USER_LOGIN, controller.login());
    }

    @Test
    void ShouldReturnLoginErrorPage() {
        assertEquals(USER_LOGIN_ERROR, controller.loginError());
    }
}
