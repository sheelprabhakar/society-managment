package com.c4c.housing.core.service;

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
    String authenticate(String username, String password, boolean isOtp) throws Exception;
}
