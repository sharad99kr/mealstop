package com.dalhousie.MealStop.security.encrypter;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.dalhousie.MealStop.common.CommonConstants.SALT_LENGTH;

@Service
public final class PasswordEncrypter {
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(SALT_LENGTH);
    }
}
