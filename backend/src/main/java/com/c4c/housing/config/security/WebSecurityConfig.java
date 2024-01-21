package com.c4c.housing.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig {
	@Value("${spring.security.debug:false}")
	boolean securityDebug;
	private static final String[] AUTH_WHITELIST = {
			"/swagger-resources",
			"/swagger-resources/**",
			"/swagger-ui.html",
			"/webjars/**",
			"/v3/api-docs/**",
			"/actuator/*",
			"/swagger-ui/**",
			"/api/v1/auth/**",
			"/error"

	};
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		JwtTokenFilter customFilter = new JwtTokenFilter(jwtTokenProvider);
		// Entry points
		return http.csrf(csrf -> csrf.disable())
		.authorizeHttpRequests((authorize) -> authorize
						.requestMatchers(AUTH_WHITELIST).permitAll()
						.anyRequest().authenticated())
				.httpBasic(withDefaults())
				.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class)
				.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.build();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.debug(securityDebug).ignoring().requestMatchers(AUTH_WHITELIST)
				.requestMatchers("/css/**", "/js/**", "/img/**", "/lib/**", "/favicon.ico");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12);
	}

}
