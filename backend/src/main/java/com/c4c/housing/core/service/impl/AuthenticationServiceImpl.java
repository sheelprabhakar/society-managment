package com.c4c.housing.core.service.impl;

import com.c4c.housing.config.security.JwtTokenProvider;
import com.c4c.housing.core.entity.UserEntity;
import com.c4c.housing.core.service.AuthenticationService;
import com.c4c.housing.core.service.UserService;
import com.c4c.housing.core.service.UserTokenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;

    private final UserService userService;

    private final UserTokenService userTokenService;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public AuthenticationServiceImpl(final JwtTokenProvider jwtTokenProvider, final UserDetailsService userDetailsService,
                                     final UserService userService, final UserTokenService userTokenService,
                                     final PasswordEncoder passwordEncoder) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.userTokenService = userTokenService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String authenticate(final String username, final String password, final boolean isOtp) throws Exception {

        UserEntity userEntity = this.userService.findByEmail(username);
        if(userEntity == null){
            log.info("USER_NOT_FOUND");
            throw new BadCredentialsException("USER_NOT_FOUND");
        }
        String encodePwd = "";
        if(isOtp ){
            if(this.userService.isOTPRequired(userEntity)) {
                throw new BadCredentialsException("OTP_EXPIRED");
            }
            encodePwd = userEntity.getOtp();
        }else {
            encodePwd = userEntity.getPasswordHash();
        }
        if(this.passwordEncoder.matches(password, encodePwd)) {
            final UserDetails userDetails = this.userDetailsService
                    .loadUserByUsername(username);
            log.info("Authenticated successfully");
            String token = this.jwtTokenProvider.createToken(userDetails.getUsername(),
                    (Set<GrantedAuthority>) userDetails.getAuthorities());
            this.userTokenService.update(userEntity.getId(), token);
            return token;
        }else{
            log.info("Authenticated failed");
            throw new BadCredentialsException("INVALID_CREDENTIALS");
        }

    }

}
