package com.c4c.housing.config.security;

import com.c4c.housing.common.exception.CustomException;
import com.c4c.housing.core.entity.UserEntity;
import com.c4c.housing.core.entity.UserTokenEntity;
import com.c4c.housing.core.service.UserService;
import com.c4c.housing.core.service.UserTokenService;
import com.c4c.housing.core.service.impl.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The type Jwt token provider.
 */
@Component
@Slf4j
public class JwtTokenProvider {

    /**
     * The constant BEARER_LENGTH.
     */
    private static final int BEARER_LENGTH = 7;
    /**
     * The constant FIVE_.
     */
    public static final int FIVE_ = 5;

    /**
     * The Secret key.
     */
    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;

    /**
     * The Validity in milliseconds.
     */
    @Value("${security.jwt.token.expire-length:3600000}")
    private final long validityInMilliseconds = 3600000L; // 1h

    /**
     * The User details service.
     */
    private final UserDetailsServiceImpl userDetailsService;

    /**
     * The User token service.
     */
    private final UserTokenService userTokenService;
    /**
     * The User service.
     */
    private final UserService userService;

    /**
     * Instantiates a new Jwt token provider.
     *
     * @param userDetailsService the user details service
     * @param userTokenService   the user token service
     * @param userService        the user service
     */
    public JwtTokenProvider(final UserDetailsServiceImpl userDetailsService, final UserTokenService userTokenService,
                            final UserService userService) {
        this.userDetailsService = userDetailsService;
        this.userTokenService = userTokenService;
        this.userService = userService;
    }

    /**
     * Init.
     */
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    /**
     * Create token string.
     *
     * @param username the username
     * @param roles    the roles
     * @return the string
     */
    public String createToken(final String username, final Set<GrantedAuthority> roles) {

        Claims claims = Jwts.claims().setSubject(username).add("authorities", roles.stream().map(
                s -> new SimpleGrantedAuthority(s.getAuthority()))
                .filter(Objects::nonNull).collect(Collectors.toList())).build();

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()//
                .setClaims(claims)//
                .setIssuedAt(now)//
                .setExpiration(validity)//
                .signWith(SignatureAlgorithm.HS256, secretKey)//
                .compact();
    }

    /**
     * Create refresh token string.
     *
     * @param username the username
     * @return the string
     */
    public String createRefreshToken(final String username) {

        Claims claims = Jwts.claims().setSubject(username).build();

        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, FIVE_);
        Date validity = new Date(c.getTimeInMillis() + validityInMilliseconds);

        return Jwts.builder()//
                .setClaims(claims)//
                .setIssuedAt(new Date(c.getTimeInMillis()))//
                .setExpiration(validity)//
                .signWith(SignatureAlgorithm.HS256, secretKey)//
                .compact();
    }

    /**
     * Gets authentication.
     *
     * @param token the token
     * @return the authentication
     */
    public Authentication getAuthentication(final String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        UserEntity user = this.userService.findByEmail(userDetails.getUsername());
        if (user != null) {
            UserTokenEntity userTokenEntity = this.userTokenService.getById(user.getId());
            if (userTokenEntity == null || !userTokenEntity.getAccessToken().equals(token)) {
                throw new CustomException("Invalid token", HttpStatus.UNAUTHORIZED);
            }
        }
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     * Gets username.
     *
     * @param token the token
     * @return the username
     */
    public String getUsername(final String token) {
        return Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * Resolve token string.
     *
     * @param req the req
     * @return the string
     */
    public String resolveToken(final HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(BEARER_LENGTH);
        }
        log.info("Invalid Token");
        return null;
    }

    /**
     * Validate token boolean.
     *
     * @param token the token
     * @return the boolean
     */
    public boolean validateToken(final String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.info("Expired or invalid JWT token");
            throw new CustomException("Expired or invalid JWT token", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
