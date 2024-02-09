package com.c4c.housing.rest.resource.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * The type Jwt request.
 */
@NoArgsConstructor
@Getter
@Setter
public class JwtRequest implements Serializable {

    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = 5926468583005150707L;

    /**
     * The Username.
     */
    @NotBlank
    private String username;
    /**
     * The Password.
     */
    @NotBlank
    private String password;
    /**
     * The Is otp.
     */
    private boolean isOtp;
}
