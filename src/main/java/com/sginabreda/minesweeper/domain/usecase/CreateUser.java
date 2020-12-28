package com.sginabreda.minesweeper.domain.usecase;

import com.sginabreda.minesweeper.delivery.dto.request.UserRequestDto;
import com.sginabreda.minesweeper.domain.entity.User;
import com.sginabreda.minesweeper.domain.mapper.UserMapper;
import com.sginabreda.minesweeper.infrastructure.repository.UserRepository;

public class CreateUser {

	private final UserRepository userRepository;
	private final UserMapper userMapper;

	public CreateUser(UserRepository userRepository, UserMapper userMapper) {
		this.userRepository = userRepository;
		this.userMapper = userMapper;
	}

	public User invoke(UserRequestDto userRequest) {
		User user = userMapper.toDomain(userRequest);
		return userMapper.toDomain(userRepository.save(userMapper.toModel(user)));
	}
}
