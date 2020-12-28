package com.sginabreda.minesweeper.application.config;

import com.sginabreda.minesweeper.domain.mapper.UserMapper;
import com.sginabreda.minesweeper.domain.usecase.CreateUser;
import com.sginabreda.minesweeper.domain.usecase.GenerateToken;
import com.sginabreda.minesweeper.infrastructure.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfiguration {

	@Bean
	public CreateUser createUser(UserRepository userRepository, UserMapper userMapper) {
		return new CreateUser(userRepository, userMapper);
	}

	@Bean
	public GenerateToken generateToken() {
		return new GenerateToken();
	}

	@Bean
	public UserMapper userMapper() {
		return new UserMapper();
	}
}
