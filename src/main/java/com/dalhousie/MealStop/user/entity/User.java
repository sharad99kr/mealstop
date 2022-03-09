package com.dalhousie.MealStop.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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
