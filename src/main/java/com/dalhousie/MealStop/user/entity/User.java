package com.dalhousie.MealStop.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY)
    private long user_id;

    private String firstName;

    private String lastName;

    private String username;

    private String mobileNumber;

    private String dateOfBirth;

    private String address;

    private String password;

    private String role;

    private boolean enabled = false;

    private String token;
}
