package com.dalhousie.MealStop.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalDateTime;

@Service
public class EmailService implements IEmailService {

    //Gets a logger for email service class.
    private final static Logger EMAIL_LOGGER = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendEmail(String to, String content) {
        try {

            var message = javaMailSender.createMimeMessage();
            var messageHelper = new MimeMessageHelper(message, "utf-8");

            messageHelper.setText(content, true);
            messageHelper.setTo(to);
            messageHelper.setSubject("Confirm your email");
            messageHelper.setFrom("coder.udit@gmail.com");

            javaMailSender.send(message);

            EMAIL_LOGGER.info("Email sent to " + to + " at " + LocalDateTime.now());

        } catch (MessagingException ex) {
            EMAIL_LOGGER.error("Unable to send email: ", ex.getCause());
            throw new IllegalStateException("Unable to send email.");
        }catch (Exception ex){
            throw new IllegalStateException("Unable to send email." + ex.getMessage());
        }

    }
}
