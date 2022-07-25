package com.aamu.aamurest.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.aamu.aamurest.jwt.JwtAuthenticationEntryPoint;
import com.aamu.aamurest.jwt.JwtRequestFilter;
import com.aamu.aamurest.jwt.JwtTokenUtil;
import com.aamu.aamurest.jwt.JwtUserDetailsService;

@Configuration
public class JwtConfiguration {

	@Bean
	public JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint() {
		return new JwtAuthenticationEntryPoint();
	}

	@Bean
	public JwtRequestFilter jwtRequestFilter() {
		return new JwtRequestFilter();
	}

	@Bean
	public JwtTokenUtil jwtTokenUtil() {
		return new JwtTokenUtil();
	}

	@Bean
	public JwtUserDetailsService jwtUserDetailsService() {
		return new JwtUserDetailsService();
	}
}
