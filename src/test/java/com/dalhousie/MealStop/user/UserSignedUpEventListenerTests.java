package com.dalhousie.MealStop.user;

import com.dalhousie.MealStop.email.EmailService;
import com.dalhousie.MealStop.user.entity.User;
import com.dalhousie.MealStop.user.event.UserSignedUpEvent;
import com.dalhousie.MealStop.user.event.listener.UserSignedUpEventListener;
import com.dalhousie.MealStop.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.config.annotation.web.builders.WebSecurity;

import static org.mockito.ArgumentMatchers.any;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class UserSignedUpEventListenerTests {
    @Mock
    private UserService userServiceMock;

    @Mock
    private EmailService emailServiceMock;

    private UserSignedUpEvent userSignedUpEventMock;

    private User mockUser;

    @InjectMocks
    UserSignedUpEventListener userSignedUpEventListener;

    @Test
    void ShouldCreateEventAndSendMail(){
        mockUser = new User();
        userSignedUpEventMock = new UserSignedUpEvent(mockUser, "Random Url");
        Mockito.lenient().doNothing().when(userServiceMock).saveVerificationTokenForUser(any(User.class), any(String.class));
        userSignedUpEventListener.onApplicationEvent(userSignedUpEventMock);
    }

}
