package com.dalhousie.MealStop.registration.common;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public class EmailValidator implements Predicate<String> {

    //Regular Expression by RFC 5322 for Email Validation
    private String regexPattern = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

    @Override
    public boolean test(String email) {
        return regexPattern.matches(email);
    }
}
