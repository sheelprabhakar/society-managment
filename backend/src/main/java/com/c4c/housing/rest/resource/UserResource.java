package com.c4c.housing.rest.resource;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;

@Data
@NoArgsConstructor
public class UserResource {
    private Long id;

    private String firstName;

    private String middleName;

    private String lastName;

    private String mobile;

    private String email;

    private String passwordHash;

    private Calendar registeredAt;

    private Calendar lastLogin;

    private String intro;
    private String profile;

}
