package com.dalhousie.MealStop.user;

import com.dalhousie.MealStop.common.VerificationTokenConstants;
import com.dalhousie.MealStop.user.entity.PasswordResetToken;
import com.dalhousie.MealStop.user.entity.User;
import com.dalhousie.MealStop.user.entity.VerificationToken;
import com.dalhousie.MealStop.user.models.UserModel;
import com.dalhousie.MealStop.user.repository.PasswordResetTokenRepository;
import com.dalhousie.MealStop.user.repository.UserRepository;
import com.dalhousie.MealStop.user.repository.VerificationTokenRepository;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private VerificationTokenRepository mockVerificationTokenRepository;

    @Mock
    private PasswordResetTokenRepository mockPasswordResetTokenRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private UserModel mockUserModel;

    @Mock
    private User mockUser;

    @Mock
    private VerificationToken mockVerificationToken;

    @Mock
    private Date mockDate;

    @Mock
    private PasswordResetToken mockPasswordResetToken;

    @Mock
    private List<User> mockUsers;

    @Mock
    private UserDetails mockUserDetails;

    @InjectMocks
    private UserService userService;

    private String mockToken;

    private String mockPassword;

    private String mockEmail;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockToken = "token";
        mockPassword = "password";
        mockEmail = "email";
        Mockito.lenient().when(mockUserRepository.save(mockUser)).thenReturn(mockUser);
        Mockito.lenient().when(mockVerificationTokenRepository.save(mockVerificationToken)).thenReturn(mockVerificationToken);
        Mockito.lenient().when(mockVerificationTokenRepository.findByToken(mockToken)).thenReturn(mockVerificationToken);
        Mockito.lenient().when(mockPasswordResetTokenRepository.save(mockPasswordResetToken)).thenReturn(mockPasswordResetToken);
        Mockito.lenient().when(mockPasswordResetTokenRepository.findByToken(mockToken)).thenReturn(mockPasswordResetToken);
        Mockito.lenient().when(mockPasswordResetTokenRepository.findByToken(mockToken).getUser()).thenReturn(mockUser);
        Mockito.lenient().when(mockUserRepository.findAll()).thenReturn(mockUsers);
        Mockito.lenient().when(mockUserRepository.findAll().size()).thenReturn(5);
        Mockito.lenient().when(mockUserRepository.findByUsername(mockEmail)).thenReturn(mockUser);
        Mockito.lenient().when(mockUserRepository.findByUsername(mockEmail).getRole()).thenReturn("Customer");
        Mockito.lenient().when(mockUserRepository.findByUsername(mockEmail).getUsername()).thenReturn(mockEmail);
        Mockito.lenient().when(mockUserRepository.findByUsername(mockEmail).getPassword()).thenReturn(mockPassword);
        Mockito.lenient().when(mockUser.getPassword()).thenReturn(mockPassword);
        mockUserModel = new UserModel();
        mockUserModel.setRole("Customer");
    }

    @Test
    void ShouldSignUpUser() {
        assertDoesNotThrow(() -> userService.signUpUser(mockUserModel));
    }


    @Test
    void ShouldSaveVerificationToken() {
        assertDoesNotThrow(() -> userService.saveVerificationTokenForUser(mockUser, mockToken));
    }

    @Test
    void ShouldReturnInvalidVerificationToken() {
        assertEquals(VerificationTokenConstants.INVALID, userService.validateVerificationToken("I am a token."));
    }

    @Test
    void ShouldReturnExpiredVerificationToken() {
        Mockito.lenient().when(mockVerificationToken.getUser()).thenReturn(mockUser);
        Mockito.lenient().when(mockVerificationToken.getExpirationTime()).thenReturn(mockDate);
        Mockito.lenient().when(mockVerificationToken.getExpirationTime().getTime()).thenReturn(System.currentTimeMillis() - 150000);
        assertEquals(VerificationTokenConstants.EXPIRED, userService.validateVerificationToken(mockToken));
    }

    @Test
    void ShouldReturnValidVerificationToken() {
        Mockito.lenient().when(mockVerificationToken.getUser()).thenReturn(mockUser);
        Mockito.lenient().when(mockVerificationToken.getExpirationTime()).thenReturn(mockDate);
        Mockito.lenient().when(mockVerificationToken.getExpirationTime().getTime()).thenReturn(System.currentTimeMillis() + 150000);
        assertEquals(VerificationTokenConstants.VALID, userService.validateVerificationToken(mockToken));
    }

    @Test
    void ShouldCreatePasswordToken() {
        assertDoesNotThrow(() -> userService.createPasswordResetTokenForUser(mockUser, mockToken));
    }

    @Test
    void ShouldReturnInvalidPasswordToken() {
        assertEquals(VerificationTokenConstants.INVALID, userService.validatePasswordResetToken("I am a token."));
    }

    @Test
    void ShouldReturnExpiredPasswordToken() {
        Mockito.lenient().when(mockPasswordResetToken.getExpirationTime()).thenReturn(mockDate);
        Mockito.lenient().when(mockPasswordResetToken.getExpirationTime().getTime()).thenReturn(System.currentTimeMillis() - 150000);
        assertEquals(VerificationTokenConstants.EXPIRED, userService.validatePasswordResetToken(mockToken));
    }

    @Test
    void ShouldReturnValidPasswordToken() {
        Mockito.lenient().when(mockPasswordResetToken.getExpirationTime()).thenReturn(mockDate);
        Mockito.lenient().when(mockPasswordResetToken.getExpirationTime().getTime()).thenReturn(System.currentTimeMillis() + 150000);
        assertEquals(VerificationTokenConstants.VALID, userService.validatePasswordResetToken(mockToken));
    }

    @Test
    void ShouldReturnOptionalUserForPasswordToken() {
        assertNotNull(userService.getUserByPasswordResetToken(mockToken));
    }

    @Test
    void ShouldChangePassword() {
        assertDoesNotThrow(() -> userService.changePassword(mockUser, mockPassword));
    }

    @Test
    void ShouldReturnFalseForInvalidOldPassword() {
        assertFalse(userService.checkIfValidOldPassword(mockUser, "mockPassword"));
    }


    @Test
    void ShouldSaveUser() {
        assertDoesNotThrow(() -> userService.saveUser(mockUser));
    }

    @Test
    void ShouldGetUsers() {
        assertTrue(userService.getUsers().size() > 0);
    }

    @Test
    void ShouldFindUserByEmail() {
        assertNotNull(userService.findUserByEmail(mockEmail));
    }

    @Test
    void ShouldReturnNullForUserNotFound() {
        assertNull(userService.loadUserByUsername("mockEmail"));
    }

    @Test
    void ShouldReturnUserDetailsForUserFound() {
        assertNotNull(userService.loadUserByUsername(mockEmail));
    }

}
