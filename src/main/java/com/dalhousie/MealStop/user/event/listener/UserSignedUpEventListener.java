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

import static com.dalhousie.MealStop.common.UrlConstants.VERIFY_REGISTRATION_URL;
import static com.dalhousie.MealStop.common.UserMessagesConstants.MEALSTOP_REGISTRATION;
import static com.dalhousie.MealStop.common.UserMessagesConstants.VERIFY_URL;

@Component
@Slf4j
public class UserSignedUpEventListener implements ApplicationListener<UserSignedUpEvent> {

    @Autowired
    private IUserService userService;

    @Autowired
    private IEmailService emailService;

    @Override
    public void onApplicationEvent(UserSignedUpEvent event) {
        //Create the verification token for the user with the link.
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.saveVerificationTokenForUser(user, token);

        //Send mail to the user.
        String url = event.getApplicationUrl() + VERIFY_REGISTRATION_URL + token;

        log.info(VERIFY_URL + url);
        emailService.sendEmail(user.getUsername(), VERIFY_URL + url, MEALSTOP_REGISTRATION);
        return;
    }

}
