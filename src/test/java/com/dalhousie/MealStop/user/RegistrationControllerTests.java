package com.dalhousie.MealStop.user;

import com.dalhousie.MealStop.common.VerificationTokenConstants;
import com.dalhousie.MealStop.email.IEmailService;
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
import org.springframework.validation.BindingResult;
import javax.servlet.http.HttpServletRequest;
import static com.dalhousie.MealStop.common.RoleEnum.ROLE_CUSTOMER;
import static com.dalhousie.MealStop.common.UrlConstants.*;
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

    private PasswordModel mockPasswordModel;

    @Mock
    private BindingResult mockBindingResult;

    @Mock
    private IEmailService mockEmailService;

    @InjectMocks
    private RegistrationController controller;

    private String mockServerName;
    private String mockServerPort;
    private String mockContextPath;
    private String mockToken;
    private String mockEmail;
    private String mockUrl;
    private String mockPassword;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockServerName = "mealstopserver";
        mockServerPort = "8080";
        mockContextPath = "root";
        mockToken = "token";
        mockEmail = "email";
        mockUrl = "url";
        mockPassword = "password";

        mockPasswordModel = new PasswordModel();
        mockPasswordModel.setEmail(mockEmail);
        mockPasswordModel.setOldpassword(mockPassword);
        mockPasswordModel.setNewpassword(mockPassword);
        Mockito.lenient().when(mockHttpServletRequest.getServerName()).thenReturn(mockServerName);
        Mockito.lenient().when(mockHttpServletRequest.getServerPort()).thenReturn(Integer.parseInt(mockServerPort));
        Mockito.lenient().when(mockHttpServletRequest.getContextPath()).thenReturn(mockContextPath);
        Mockito.lenient().when(mockUserService.signUpUser(mockUserModel)).thenReturn(mockUser);
        mockUserModel = new UserModel();
        mockUserModel.setRole(String.valueOf(ROLE_CUSTOMER));
    }

    @Test
    void ShouldReturnRegistrationForm() {
        assertEquals(USER_REGISTER_URL, controller.showRegistrationForm(mockUserModel));
    }

    @Test
    void ShouldReturnNgoRegistrationForm() {
        assertEquals(NGO_USER_REGISTER_URL, controller.showNgoRegistrationForm(mockUserModel));
    }

    @Test
    void ShouldReturnForgotPasswordUrl() {
        assertEquals(USER_FORGOT_PASSWORD_URL, controller.showForgotPasswordForm());
    }

    @Test
    void ShouldReturnUserLoginForInvalidSavePasswordToken() {
        Mockito.lenient().when(mockUserService.validatePasswordResetToken(mockToken)).thenReturn(VerificationTokenConstants.INVALID);
        assertEquals(USER_LOGIN, controller.showSavePasswordForm(mockToken, mockPasswordModel));
    }

    @Test
    void ShouldReturnChangePasswordForValidSavePasswordToken() {
        Mockito.lenient().when(mockUserService.validatePasswordResetToken(mockToken)).thenReturn(VerificationTokenConstants.VALID);
        assertEquals(USER_CHANGE_PASSWORD_URL, controller.showSavePasswordForm(mockToken, mockPasswordModel));
    }

    @Test
    void ShouldReturnInternalServerErrorSignUpUserWhenSaveFails() {
        Mockito.lenient().when(mockUserService.signUpUser(mockUserModel)).thenThrow(NullPointerException.class);
        assertEquals(USER_LOGIN, controller.signUpUser(mockUserModel, mockBindingResult, mockHttpServletRequest));
    }

    @Test
    void ShouldReturnUserRegistrationForErrorsInBindingResult() {
        Mockito.lenient().when(mockBindingResult.hasErrors()).thenReturn(true);
        Mockito.lenient().when(mockUserService.signUpUser(mockUserModel)).thenThrow(NullPointerException.class);
        assertEquals(USER_REGISTER_URL, controller.signUpUser(mockUserModel, mockBindingResult, mockHttpServletRequest));
    }

    @Test
    void ShouldReturnUserLoginForCorrectSignUpUser() {
        Mockito.lenient().when(mockBindingResult.hasErrors()).thenReturn(false);
        assertEquals(USER_LOGIN, controller.signUpUser(mockUserModel, mockBindingResult, mockHttpServletRequest));
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
    void ShouldReturnChangePasswordWhenErrorsForSavePassword() {
        Mockito.lenient().when(mockBindingResult.hasErrors()).thenReturn(true);
        assertEquals(USER_CHANGE_PASSWORD_URL, controller.savePassword(mockPasswordModel, mockBindingResult));
    }

    @Test
    void ShouldReturnUserLoginWhenSuccess() {
        Mockito.lenient().when(mockBindingResult.hasErrors()).thenReturn(false);
        assertEquals(USER_LOGIN, controller.savePassword(mockPasswordModel, mockBindingResult));
    }


    @Test
    void ShouldReturnBadRequestChangePasswordWhenNoUser() {
        Mockito.lenient().when(mockUserService.findUserByEmail(mockEmail)).thenReturn(null);
        assertEquals(USER_CHANGE_PASSWORD_URL, controller.changePassword(mockPasswordModel));
    }

    @Test
    void ShouldReturnBadRequestChangePasswordWhenPasswordNotSame() {
        Mockito.lenient().when(mockUserService.checkIfValidOldPassword(mockUser, mockPassword)).thenReturn(false);
        assertEquals(USER_CHANGE_PASSWORD_URL, controller.changePassword(mockPasswordModel));
    }

    @Test
    void ShouldReturnOkChangePassword() {
        Mockito.lenient().when(mockUserService.findUserByEmail(mockEmail)).thenReturn(mockUser);
        Mockito.lenient().when(mockUserService.checkIfValidOldPassword(mockUser, mockPasswordModel.getOldpassword())).thenReturn(true);
        assertEquals(USER_LOGIN, controller.changePassword(mockPasswordModel));
    }

    @Test
    void ShouldReturnUserLoginForNoUser() {
        Mockito.lenient().when(mockUserService.findUserByEmail(mockEmail)).thenReturn(null);
        assertEquals(USER_LOGIN, controller.forgotPassword(mockPasswordModel, mockHttpServletRequest));
    }

    @Test
    void ShouldReturnForgotPassword() {
        Mockito.lenient().when(mockUserService.findUserByEmail(mockEmail)).thenReturn(mockUser);
        assertEquals(USER_LOGIN, controller.forgotPassword(mockPasswordModel, mockHttpServletRequest));
    }
}
