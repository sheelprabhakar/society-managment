package com.c4c.housing.core.service.impl;

import com.c4c.housing.config.security.JwtTokenProvider;
import com.c4c.housing.core.entity.RoleEntity;
import com.c4c.housing.core.entity.UserEntity;
import com.c4c.housing.core.service.AuthenticationService;
import com.c4c.housing.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public AuthenticationServiceImpl(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService, UserService userService,
                                     PasswordEncoder passwordEncoder) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String authenticate(String username, String password, boolean isOtp) throws Exception {

        UserEntity userEntity = this.userService.findByEmail(username);
        if(userEntity == null){
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

            return this.jwtTokenProvider.createToken(userDetails.getUsername(), (Set<GrantedAuthority>) userDetails.getAuthorities());
        }else{
            throw new BadCredentialsException("INVALID_CREDENTIALS");
        }

    }

}
