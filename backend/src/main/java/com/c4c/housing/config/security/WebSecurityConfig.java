package com.c4c.housing.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {

	@Autowired
	private JwtTokenProvider jwtTokenProvider;


	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// Entry points
		http.csrf(csrf -> csrf.disable())
		.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeRequests()//
			.requestMatchers("/api/accounts/signin").permitAll()//
			.requestMatchers("/api/accounts/signup").permitAll()//
			.requestMatchers("/api/accounts/message").permitAll()//
			.requestMatchers("/h2-console/**/**").permitAll()
			.anyRequest().authenticated();

		// Apply JWT
		http.apply(new JwtTokenFilterConfigurer(jwtTokenProvider));

		// Optional, if you want to test the API from a browser
		// http.httpBasic();
		return http.build();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers("/v2/api-docs")//
				.requestMatchers("/swagger-resources/**")//
				.requestMatchers("/swagger-ui.html")//
				.requestMatchers("/configuration/**")//
				.requestMatchers("/webjars/**")//
				.requestMatchers("/public")
				.and().ignoring().requestMatchers("/h2-console/**/**");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12);
	}
}
