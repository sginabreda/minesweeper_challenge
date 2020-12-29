package com.sginabreda.minesweeper.domain.usecase;

import com.sginabreda.minesweeper.delivery.dto.request.UserRequestDto;
import com.sginabreda.minesweeper.domain.entity.User;
import com.sginabreda.minesweeper.domain.exception.RequestException;
import com.sginabreda.minesweeper.domain.mapper.UserMapper;
import com.sginabreda.minesweeper.infrastructure.repository.model.UserModel;
import com.sginabreda.minesweeper.infrastructure.service.UserService;
import org.springframework.http.HttpStatus;

import java.util.Optional;

public class CreateUser {

	private final UserService userService;
	private final UserMapper userMapper;

	public CreateUser(UserService userService, UserMapper userMapper) {
		this.userService = userService;
		this.userMapper = userMapper;
	}

	public User invoke(UserRequestDto userRequest) throws RequestException {
		User user = userMapper.toDomain(userRequest);
		Optional<UserModel> userModel = userService.findByUserName(user.getUsername());
		if (userModel.isPresent()) {
			throw new RequestException("User already exists", "user.conflict", HttpStatus.CONFLICT.value());
		}
		return userMapper.toDomain(userService.save(userMapper.toModel(user)));
	}
}
