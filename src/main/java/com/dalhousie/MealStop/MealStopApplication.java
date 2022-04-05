package com.dalhousie.MealStop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.dalhousie.MealStop.common.CommonConstants.SALT_LENGTH;

@SpringBootApplication
public class MealStopApplication {

    public static void main(String[] args) {
        SpringApplication.run(MealStopApplication.class, args);
    }
}
