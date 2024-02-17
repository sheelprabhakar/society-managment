package com.c4c.housing.core.service;

import com.c4c.housing.core.entity.UserTokenEntity;
import jakarta.servlet.http.HttpServletRequest;

/**
 * The interface Authentication service.
 */
public interface AuthenticationService {
    /**
     * Authenticate string.
     *
     * @param username the username
     * @param password the password
     * @param isOtp    the is otp
     * @return the string
     * @throws Exception the exception
     */
    UserTokenEntity authenticate(String username, String password, boolean isOtp) throws Exception;

    /**
     * Logout.
     */
    void logout();

    /**
     * Refresh token user token entity.
     *
     * @param request the request
     * @return the user token entity
     */
    UserTokenEntity refreshToken(HttpServletRequest request);
}
