package com.sginabreda.minesweeper.application.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sginabreda.minesweeper.application.filter.AuthorizationFilter;
import com.sginabreda.minesweeper.infrastructure.service.UserService;
import com.sginabreda.minesweeper.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final ObjectMapper mapper = new ObjectMapper();
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
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
				.addFilterBefore(new AuthorizationFilter(mapper, jwtTokenUtil), UsernamePasswordAuthenticationFilter.class)
				.authorizeRequests()
				.anyRequest().authenticated();
	}
}
