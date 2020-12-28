package com.sginabreda.minesweeper.domain.usecase;

import com.sginabreda.minesweeper.delivery.dto.request.UserRequestDto;
import com.sginabreda.minesweeper.domain.entity.User;
import com.sginabreda.minesweeper.domain.mapper.UserMapper;
import com.sginabreda.minesweeper.infrastructure.service.UserService;

public class CreateUser {

	private final UserService userService;
	private final UserMapper userMapper;

	public CreateUser(UserService userService, UserMapper userMapper) {
		this.userService = userService;
		this.userMapper = userMapper;
	}

	public User invoke(UserRequestDto userRequest) {
		User user = userMapper.toDomain(userRequest);
		return userMapper.toDomain(userService.save(userMapper.toModel(user)));
	}
}
