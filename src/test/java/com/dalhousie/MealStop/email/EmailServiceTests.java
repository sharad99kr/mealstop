package com.dalhousie.MealStop.email;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class EmailServiceTests {
    @Mock
    private JavaMailSender mockJavaMailSender = new JavaMailSenderImpl();

    @InjectMocks
    private EmailService emailService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        emailService = new EmailService(mockJavaMailSender);
        ReflectionTestUtils.setField(emailService, "from", "from");
    }

    @Test
    void ShouldNotThrowErrorForSuccessFullEmail(){
        doNothing().when(mockJavaMailSender).send(any(SimpleMailMessage.class));
        emailService.sendEmail("to", "content", "subject");
    }
}
