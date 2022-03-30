package com.dalhousie.MealStop.user.event.listener;

import com.dalhousie.MealStop.email.IEmailService;
import com.dalhousie.MealStop.user.entity.User;
import com.dalhousie.MealStop.user.event.UserSignedUpEvent;
import com.dalhousie.MealStop.user.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class UserSignedUpEventListener implements ApplicationListener<UserSignedUpEvent> {

    @Autowired
    private IUserService IUserService;

    @Autowired
    private IEmailService emailService;

    private static final String VERIFY_REGISTRATION_URL = "/api/v1/verifyRegistration?token=";

    @Override
    public void onApplicationEvent(UserSignedUpEvent event) {
        //Create the verification token for the user with the link.
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        IUserService.saveVerificationTokenForUser(user, token);

        //Send mail to the user.
        String url = event.getApplicationUrl() + VERIFY_REGISTRATION_URL + token;

        log.info("Verify url:" + url);
        emailService.sendEmail("", "");
        //sendVerificationEmail();
    }

}
