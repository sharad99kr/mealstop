package com.dalhousie.MealStop.user.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    @Size(min = 3, max = 50)
    private String firstName;

    @Size(min = 3, max = 50)
    private String lastName;

    @NotBlank
    @Email(message = "Please enter a valid e-mail address")
    private String email;

    @Size(min = 10, max = 10)
    private String mobileNumber;

    @NotBlank
    private String dateOfBirth;

    @Size(max = 200)
    private String address;

    @NotBlank
    private String password;

    @NotBlank
    private String matchingPassword;

    @NotBlank
    private String role;
}
