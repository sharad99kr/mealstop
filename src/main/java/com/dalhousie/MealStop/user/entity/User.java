package com.dalhousie.MealStop.user.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY)
    private long userid;

    private String firstName;

    private String lastName;

    private String email;

    private String mobileNumber;

    private String dateOfBirth;

    private String address;

    private String password;

    private String role;

    private boolean enabled = false;
}
