package com.c4c.housing.core.service;

public interface AuthenticationService {
    String authenticate(String username, String password, boolean isOtp) throws Exception;
}
