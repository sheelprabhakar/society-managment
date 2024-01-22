package com.c4c.housing.rest.resource;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.UUID;

@Data
@NoArgsConstructor
public class UserResource extends CommonResourceAttributes{
    private UUID id;
    
    private String firstName;

    private String middleName;

    private String lastName;

    @NotBlank
    private String mobile;

    @NotBlank
    private String email;

    private String passwordHash;

    private Calendar lastLogin;

    private String intro;
    private String profile;

    private boolean isLocked;

}
