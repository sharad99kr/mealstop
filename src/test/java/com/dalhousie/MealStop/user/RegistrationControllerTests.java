package com.dalhousie.MealStop.user;

import com.dalhousie.MealStop.common.VerificationTokenConstants;
import com.dalhousie.MealStop.user.controller.RegistrationController;
import com.dalhousie.MealStop.user.entity.User;
import com.dalhousie.MealStop.user.event.UserSignedUpEvent;
import com.dalhousie.MealStop.user.models.PasswordModel;
import com.dalhousie.MealStop.user.models.UserModel;
import com.dalhousie.MealStop.user.service.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class RegistrationControllerTests {

    @Mock
    private IUserService mockUserService;

    @Mock
    private UserModel mockUserModel;

    @Mock
    private User mockUser;

    @Mock
    private HttpServletRequest mockHttpServletRequest;

    @Mock
    private ApplicationEventPublisher mockEventPublisher;

    @Mock
    private UserSignedUpEvent mockUserSignedUpEvent;

    @Mock
    private PasswordModel mockPasswordModel;

    @InjectMocks
    private RegistrationController controller;

    private String mockServerName;
    private String mockServerPort;
    private String mockContextPath;
    private String mockToken;
    private String mockEmail;
    private String mockPassword;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockServerName = "mealstopserver";
        mockServerPort = "8080";
        mockContextPath = "root";
        mockToken = "token";
        mockEmail = "email";
        mockPassword = "password";

        Mockito.lenient().when(mockHttpServletRequest.getServerName()).thenReturn(mockServerName);
        Mockito.lenient().when(mockHttpServletRequest.getServerPort()).thenReturn(Integer.parseInt(mockServerPort));
        Mockito.lenient().when(mockHttpServletRequest.getContextPath()).thenReturn(mockContextPath);
        Mockito.lenient().when(mockUserService.signUpUser(mockUserModel)).thenReturn(mockUser);
        Mockito.lenient().when(mockPasswordModel.getEmail()).thenReturn(mockEmail);

    }

    @Test
    void ShouldReturnInternalServerErrorSignUpUserWhenSaveFails() {
        Mockito.lenient().when(mockUserService.signUpUser(mockUserModel)).thenThrow(NullPointerException.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, controller.signUpUser(mockUserModel, mockHttpServletRequest).getStatusCode());
    }

    @Test
    void ShouldReturnCreatedSignUpUser() {
        assertEquals(HttpStatus.CREATED, controller.signUpUser(mockUserModel, mockHttpServletRequest).getStatusCode());
    }

    @Test
    void ShouldReturnBadRequestVerifyRegistrationForInvalidAndExpiredToken() {
        Mockito.lenient().when(mockUserService.validateVerificationToken(mockToken)).thenReturn(VerificationTokenConstants.INVALID);
        assertEquals(HttpStatus.BAD_REQUEST, controller.verifyRegistration(mockToken).getStatusCode());
        Mockito.lenient().when(mockUserService.validateVerificationToken(mockToken)).thenReturn(VerificationTokenConstants.EXPIRED);
        assertEquals(HttpStatus.BAD_REQUEST, controller.verifyRegistration(mockToken).getStatusCode());
    }


    @Test
    void ShouldReturnOkVerifyRegistration() {
        Mockito.lenient().when(mockUserService.validateVerificationToken(mockToken)).thenReturn(VerificationTokenConstants.VALID);
        assertEquals(HttpStatus.OK, controller.verifyRegistration(mockToken).getStatusCode());
    }

    @Test
    void ShouldReturnBadRequestResetPasswordWhenUserNotFound() {
        assertEquals(HttpStatus.BAD_REQUEST, controller.resetPassword(mockPasswordModel, mockHttpServletRequest).getStatusCode());
    }

    @Test
    void ShouldReturnOkResetPassword() {
        Mockito.lenient().when(mockUserService.findUserByEmail(mockEmail)).thenReturn(mockUser);
        assertEquals(HttpStatus.OK, controller.resetPassword(mockPasswordModel, mockHttpServletRequest).getStatusCode());
    }

    @Test
    void ShouldReturnBadRequestSavePasswordWhenInvalidToken() {
        Mockito.lenient().when(mockUserService.validatePasswordResetToken(mockToken)).thenReturn(VerificationTokenConstants.INVALID);
        assertEquals(HttpStatus.BAD_REQUEST, controller.savePassword(mockToken, mockPasswordModel).getStatusCode());
    }

    @Test
    void ShouldReturnBadRequestSavePasswordWhenNoUser() {
        Mockito.lenient().when(mockUserService.validatePasswordResetToken(mockToken)).thenReturn(VerificationTokenConstants.VALID);
        Mockito.lenient().when(mockUserService.getUserByPasswordResetToken(mockToken)).thenReturn(null);
        assertEquals(HttpStatus.BAD_REQUEST, controller.savePassword(mockToken, mockPasswordModel).getStatusCode());
    }

    @Test
    void ShouldReturnOkSavePassword() {
        Mockito.lenient().when(mockUserService.validatePasswordResetToken(mockToken)).thenReturn(VerificationTokenConstants.VALID);
        Mockito.lenient().when(mockUserService.getUserByPasswordResetToken(mockToken)).thenReturn(Optional.of(mockUser));
        assertEquals(HttpStatus.OK, controller.savePassword(mockToken, mockPasswordModel).getStatusCode());
    }

    @Test
    void ShouldReturnBadRequestChangePasswordWhenNoUser() {
        Mockito.lenient().when(mockUserService.findUserByEmail(mockEmail)).thenReturn(null);
        assertEquals(HttpStatus.BAD_REQUEST, controller.changePassword(mockPasswordModel).getStatusCode());
    }

    @Test
    void ShouldReturnBadRequestChangePasswordWhenPasswordNotSame() {
        Mockito.lenient().when(mockUserService.checkIfValidOldPassword(mockUser, mockPassword)).thenReturn(false);
        assertEquals(HttpStatus.BAD_REQUEST, controller.changePassword(mockPasswordModel).getStatusCode());
    }

    @Test
    void ShouldReturnOkChangePassword() {
        Mockito.lenient().when(mockUserService.checkIfValidOldPassword(mockUser, mockPassword)).thenReturn(true);
        assertEquals(HttpStatus.BAD_REQUEST, controller.changePassword(mockPasswordModel).getStatusCode());
    }
}
