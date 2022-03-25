package com.dalhousie.MealStop.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

import static com.dalhousie.MealStop.common.UrlConstants.*;
import static com.dalhousie.MealStop.common.UserMessagesConstants.*;

@Controller
@Slf4j
public class LoginController {

    @GetMapping(LOGIN_URL)
    public String login() {
        log.info(LOGIN_TRYING_MSG + LocalDateTime.now());
        return USER_LOGIN;
    }

    @GetMapping(LOGIN_ERROR)
    public String loginError() {
        log.info(LOGIN_ERROR_MSG + LocalDateTime.now());
        return USER_LOGIN_ERROR;
    }
}
