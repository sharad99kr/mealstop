package com.dalhousie.MealStop.email;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class EmailConfigurationTests {
    private EmailConfig emailConfig;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        emailConfig = new EmailConfig();
        ReflectionTestUtils.setField(emailConfig, "host", "host");
        ReflectionTestUtils.setField(emailConfig, "port", "23");
        ReflectionTestUtils.setField(emailConfig, "username", "username");
        ReflectionTestUtils.setField(emailConfig, "password", "password");
        ReflectionTestUtils.setField(emailConfig, "auth", "auth");
        ReflectionTestUtils.setField(emailConfig, "starttls", "true");
    }

    @Test
    void ShouldReturnMailSender() {
        assertDoesNotThrow(() -> emailConfig.getJavaMailSender());
    }
}
