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
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtTokenProvider {

	@Value("${security.jwt.token.secret-key:secret-key}")
	private String secretKey;

	@Value("${security.jwt.token.expire-length:3600000}")
	private long validityInMilliseconds = 3600000*24*365; // 1h

	private final UserDetailsServiceImpl userDetailsService;

	private final UserTokenService userTokenService;
	private final UserService userService;

    public JwtTokenProvider(UserDetailsServiceImpl userDetailsService, UserTokenService userTokenService, UserService userService) {
        this.userDetailsService = userDetailsService;
        this.userTokenService = userTokenService;
        this.userService = userService;
    }

    @PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}

	public String createToken(String username, Set<GrantedAuthority> roles) {

		Claims claims = Jwts.claims().setSubject(username).add("authorities", roles.stream().map(s -> new SimpleGrantedAuthority(s.getAuthority()))
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

	public Authentication getAuthentication(String token) {
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
		UserEntity user = this.userService.findByEmail(userDetails.getUsername());
		if(user != null) {
			UserTokenEntity userToken = this.userTokenService.getById(user.getId());
			if (!userToken.getToken().equals(token)) {
				throw new CustomException("Invalid token", HttpStatus.UNAUTHORIZED);
			}
		}
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	public String getUsername(String token) {
		return Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
	}

	public String resolveToken(HttpServletRequest req) {
		String bearerToken = req.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		log.info("Invalid Token");
		return null;
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			log.info("Expired or invalid JWT token");
			throw new CustomException("Expired or invalid JWT token", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}