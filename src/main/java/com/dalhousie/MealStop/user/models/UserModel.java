package com.dalhousie.MealStop.user.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    private String firstName;

    private String lastName;

    private String email;

    private String mobileNumber;

    private String dateOfBirth;

    private String address;

    private String password;

    private String matchingPassword;

    private String Role;
}
