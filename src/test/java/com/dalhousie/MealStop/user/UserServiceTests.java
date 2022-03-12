package com.dalhousie.MealStop.user;

import com.dalhousie.MealStop.common.VerificationTokenConstants;
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
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
//@RunWith(JUnitPlatform.class)
public class UserServiceTests {

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private VerificationTokenRepository mockVerificationTokenRepository;

    @Mock
    private PasswordResetTokenRepository mockPasswordResetTokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserModel mockUserModel;

    @Mock
    private User mockUser;

    @Mock
    private VerificationToken mockVerificationToken;

    @Mock
    private Date mockDate;

    @InjectMocks
    private UserService userService;

    private String mockToken;
    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockToken = "token";
        Mockito.lenient().when(mockUserRepository.save(mockUser)).thenReturn(mockUser);
        Mockito.lenient().when(mockVerificationTokenRepository.save(mockVerificationToken)).thenReturn(mockVerificationToken);
        Mockito.lenient().when(mockVerificationTokenRepository.findByToken(mockToken)).thenReturn(mockVerificationToken);

        /*user = new User();
        user.setEmail("test@asdc.com");
        user.setFirstName("Meal first name");
        user.setLastName("Stop last name");
        user.setAddress("Halifax, NS");
        user.setMobileNumber("9339849345");
        user.setDateOfBirth("12th July, 1986");
        user.setRole("Customer");
        user.setPassword(passwordEncoder.encode("secret"));*/
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
}
