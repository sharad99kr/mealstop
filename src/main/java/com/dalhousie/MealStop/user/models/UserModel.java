package com.dalhousie.MealStop.user.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    @NotEmpty(message = "{Null.User.FirstName}")
    @Size(min = 3, max = 50, message = "{Size.User.FirstName}")
    private String firstName;

    private String lastName;

    @NotEmpty(message = "{Null.User.Email}")
    @Email(message = "{Size.User.Email}")
    private String email;

    @NotEmpty(message = "{Null.User.MobileNumber}")
    @Size(min = 10, max = 10, message = "{Size.User.MobileNumber}")
    private String mobileNumber;

    private String dateOfBirth;

    @NotEmpty(message = "{Null.User.Address}")
    @Size(min = 10, max = 200, message = "{Size.User.Address}")
    private String address;

    @NotEmpty(message = "{Null.User.Password}")
    @Size(min = 6, max = 50, message = "{Size.User.Password}")
    private String password;

    @NotEmpty(message = "{Null.User.MatchingPassword}")
    @Size(min = 6, max = 50, message = "{Size.User.Password}")
    private String matchingPassword;

    @NotEmpty
    private String role;
}
