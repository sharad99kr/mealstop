package com.dalhousie.MealStop.user;

import com.auth0.jwt.JWT;
import com.dalhousie.MealStop.user.controller.UserController;
import com.dalhousie.MealStop.user.service.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.GrantedAuthority;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Date;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class UserControllerTests {

    @Mock
    private User mockUser;

    @Mock
    private IUserService mockUserService;

    @Mock
    private HttpServletRequest mockHttpServletRequest;

    @Mock
    private Authentication mockAuthentication;

    @Mock
    private JWT mockJWT;

    @Mock
    private Date mockDate;

    @InjectMocks
    private UserController controller;

    private String mockEmail;

    //@Spy
    //private final SpringService springJunitService = new SpringService();

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockEmail = "email";
        mockUser = new User("random user", "random user", new ArrayList<GrantedAuthority>());
        ReflectionTestUtils.setField(controller, "secret", "it's a security key");
        ReflectionTestUtils.setField(controller, "expiresAt", "1");
    }

    //@Test
    void ShouldCreateAccessTokenForUser() {
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(mockAuthentication);
        SecurityContextHolder.setContext(securityContext);
        Mockito.lenient().when(mockAuthentication.getPrincipal()).thenReturn(mockUser);
        //Mockito.lenient().when(mockHttpServletRequest.getRequestURL()).thenReturn("Random URL");
        //Mockito.lenient().when(mockJWT.create().withExpiresAt(any(Date.class))).thenReturn(mockDate);
        //assertDoesNotThrow(() -> controller.getUser(mockHttpServletRequest));
    }
}
