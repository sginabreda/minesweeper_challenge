package com.sginabreda.minesweeper.application.config;

import com.sginabreda.minesweeper.domain.mapper.UserMapper;
import com.sginabreda.minesweeper.domain.usecase.CreateUser;
import com.sginabreda.minesweeper.domain.usecase.GenerateToken;
import com.sginabreda.minesweeper.infrastructure.repository.UserRepository;
import com.sginabreda.minesweeper.infrastructure.service.UserService;
import com.sginabreda.minesweeper.util.JwtTokenUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UserConfiguration {

	@Bean
	public CreateUser createUser(UserService userService, UserMapper userMapper) {
		return new CreateUser(userService, userMapper);
	}

	@Bean
	public GenerateToken generateToken(UserMapper userMapper, JwtTokenUtil jwtTokenUtil, UserService userService) {
		return new GenerateToken(userService, userMapper, jwtTokenUtil);
	}

	@Bean
	public UserMapper userMapper(PasswordEncoder passwordEncoder) {
		return new UserMapper(passwordEncoder);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
