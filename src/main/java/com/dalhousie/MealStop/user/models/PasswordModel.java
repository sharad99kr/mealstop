package com.dalhousie.MealStop.user.models;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class PasswordModel {
    @NotEmpty(message = "{Null.User.Email}")
    @Email(message = "{Size.User.Email}")
    private String email;
    @NotEmpty(message = "{Null.User.Password}")
    @Size(min = 6, max = 50, message = "{Size.User.Password}")
    private String oldpassword;
    @NotEmpty(message = "{Null.User.Password}")
    @Size(min = 6, max = 50, message = "{Size.User.Password}")
    private String newpassword;
}
