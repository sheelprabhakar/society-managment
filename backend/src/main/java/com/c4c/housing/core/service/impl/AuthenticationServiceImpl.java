package com.c4c.housing.core.service.impl;

import com.c4c.housing.config.security.JwtTokenProvider;
import com.c4c.housing.core.entity.UserEntity;
import com.c4c.housing.core.entity.UserTokenEntity;
import com.c4c.housing.core.service.AuthenticationService;
import com.c4c.housing.core.service.UserExDetailsService;
import com.c4c.housing.core.service.UserService;
import com.c4c.housing.core.service.UserTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * The type Authentication service.
 */
@Service
@Slf4j
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {
    /**
     * The Jwt token provider.
     */
    private final JwtTokenProvider jwtTokenProvider;
    /**
     * The User details service.
     */
    private final UserExDetailsService userDetailsService;

    /**
     * The User service.
     */
    private final UserService userService;

    /**
     * The User token service.
     */
    private final UserTokenService userTokenService;
    /**
     * The Password encoder.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Instantiates a new Authentication service.
     *
     * @param jwtTokenProvider   the jwt token provider
     * @param userDetailsService the user details service
     * @param userService        the user service
     * @param userTokenService   the user token service
     * @param passwordEncoder    the password encoder
     */
    @Autowired
    public AuthenticationServiceImpl(final JwtTokenProvider jwtTokenProvider,
                                     final UserExDetailsService userDetailsService,
                                     final UserService userService, final UserTokenService userTokenService,
                                     final PasswordEncoder passwordEncoder) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.userTokenService = userTokenService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Authenticate string.
     *
     * @param username the username
     * @param password the password
     * @param isOtp    the is otp
     * @return the AuthSuccessInfo
     * @throws Exception the exception
     */
    @Override
    public UserTokenEntity authenticate(final String username,
                                        final String password, final boolean isOtp) throws Exception {

        UserEntity userEntity = this.userService.findByEmail(username);
        if (userEntity == null) {
            log.info("USER_NOT_FOUND");
            throw new BadCredentialsException("USER_NOT_FOUND");
        }
        String encodePwd = "";
        if (isOtp) {
            if (this.userService.isOTPRequired(userEntity)) {
                throw new BadCredentialsException("OTP_EXPIRED");
            }
            encodePwd = userEntity.getOtp();
        } else {
            encodePwd = userEntity.getPasswordHash();
        }
        if (this.passwordEncoder.matches(password, encodePwd)) {
            final UserDetails userDetails = this.userDetailsService
                    .loadUserByUsername(username);
            log.info("Authenticated successfully");
            String token = this.jwtTokenProvider.createToken(userDetails.getUsername(),
                    (Set<GrantedAuthority>) userDetails.getAuthorities());
            String refreshToken = this.jwtTokenProvider.createRefreshToken(userDetails.getUsername());
            return this.userTokenService.update(userEntity.getId(), token, refreshToken);
        } else {
            log.info("Authenticated failed");
            throw new BadCredentialsException("INVALID_CREDENTIALS");
        }

    }

    /**
     * Logout.
     */
    @Override
    public void logout() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        UserEntity userEntity = this.userService.findByEmail(userDetails.getUsername());
        if (userEntity == null) {
            log.info("USER_NOT_FOUND");
            throw new BadCredentialsException("USER_NOT_FOUND");
        }
        this.userTokenService.deleteById(userEntity.getId());
    }

    /**
     * Refresh token user token entity.
     *
     * @param refreshToken the token
     * @return the user token entity
     */
    @Override
    public UserTokenEntity refreshToken(final String refreshToken) {
        if (this.jwtTokenProvider.validateToken(refreshToken)) {
            String username = this.jwtTokenProvider.getUsername(refreshToken);
            UserEntity userEntity = this.userService.findByEmail(username);
            UserDetails userDetails = this.userDetailsService
                    .loadUserByUsername(userEntity);
            String token = this.jwtTokenProvider.createToken(userDetails.getUsername(),
                    (Set<GrantedAuthority>) userDetails.getAuthorities());

            String newRefreshToken = this.jwtTokenProvider.createRefreshToken(userDetails.getUsername());
            return this.userTokenService.update(userEntity.getId(), token, newRefreshToken);
        }
        return null;
    }

}
