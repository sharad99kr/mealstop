package com.dalhousie.MealStop.user.domainmodels;

public class RegistrationRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private String dateOfBirth;
    private String address;
    private String username;
    private String password;
    private String userType;


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

    public String getAddress() {
        return address;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getUserType() {
        return userType;
    }
}
