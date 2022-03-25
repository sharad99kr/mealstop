package com.dalhousie.MealStop.user.models;

import lombok.Data;

@Data
public class PasswordModel {
    private String email;
    private String oldpassword;
    private String newpassword;
}
