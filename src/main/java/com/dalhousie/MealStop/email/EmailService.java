package com.dalhousie.MealStop.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class EmailService implements IEmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Value("${spring.mail.from}")
    private String from;

    public EmailService(JavaMailSender javaMailSender){
        emailSender = javaMailSender;
    }

    @Override
    public void sendEmail(String to, String content, String subject) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);

        emailSender.send(message);

        log.info("Email sent to " + to + " at " + LocalDateTime.now());
    }
}
