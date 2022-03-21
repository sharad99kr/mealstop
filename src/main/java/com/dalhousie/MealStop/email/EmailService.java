package com.dalhousie.MealStop.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import java.util.Properties;

@Service
public class EmailService implements IEmailService {

    //Gets a logger for email service class.
    private final static Logger EMAIL_LOGGER = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSenderImpl javaMailSender;

    @Override
    public void sendEmail(String to, String content) {

        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.mailtrap.io");
        prop.put("mail.smtp.port", "2525");
        prop.put("mail.smtp.ssl.trust", "smtp.mailtrap.io");

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("coder.@gmail.com");
        message.setTo("coder.udit@gmail.com");
        message.setSubject("subject");
        message.setText("text");
        javaMailSender.send(message);

        //EMAIL_LOGGER.info("Email sent to " + to + " at " + LocalDateTime.now());
    }
}
