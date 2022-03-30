package com.dalhousie.MealStop.email;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.MessagingException;

@Configuration
public class EmailConfig {
    @Bean
    public JavaMailSenderImpl getJavaMailSender() {
        return new JavaMailSenderImpl();
    }
}
