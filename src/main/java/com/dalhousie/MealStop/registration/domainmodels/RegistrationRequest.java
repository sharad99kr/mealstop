package com.dalhousie.MealStop.registration.domainmodels;

import org.springframework.context.annotation.Bean;

public class RegistrationRequest {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String mobile;
    private final String dateOfBirth;
    private final String username;
    private final String password;

    public RegistrationRequest(String firstName, String lastName, String email, String mobile, String dateOfBirth, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobile = mobile;
        this.dateOfBirth = dateOfBirth;
        this.username = username;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
