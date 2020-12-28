package com.sginabreda.minesweeper.application.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sginabreda.minesweeper.application.filter.AuthorizationFilter;
import com.sginabreda.minesweeper.util.JwtTokenUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private ObjectMapper mapper = new ObjectMapper();
	private JwtTokenUtil jwtTokenUtil;

	@Bean
	public AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.csrf()
				.disable()
				.authorizeRequests()
				.antMatchers("/users/**").permitAll()
				.and()
				.antMatcher("/games/**")
				.addFilterBefore(new AuthorizationFilter(mapper, jwtTokenUtil), FilterSecurityInterceptor.class)
				.authorizeRequests()
				.anyRequest().authenticated();
	}
}
