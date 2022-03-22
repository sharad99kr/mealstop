package com.dalhousie.MealStop.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Properties;

@Component
public class EmailService implements IEmailService {

    //Gets a logger for email service class.
    private final static Logger EMAIL_LOGGER = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender emailSender;

    @Override
    public void sendEmail(String to, String content, String subject) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("mealstopapp@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);

        emailSender.send(message);

        EMAIL_LOGGER.info("Email sent to " + to + " at " + LocalDateTime.now());
    }
}
