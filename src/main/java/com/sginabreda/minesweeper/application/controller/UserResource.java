package com.sginabreda.minesweeper.application.controller;

import com.sginabreda.minesweeper.delivery.UserController;
import com.sginabreda.minesweeper.delivery.dto.request.UserRequestDto;
import com.sginabreda.minesweeper.delivery.dto.response.JwtTokenDto;
import com.sginabreda.minesweeper.delivery.dto.response.UserDto;
import com.sginabreda.minesweeper.domain.exception.RequestException;
import com.sginabreda.minesweeper.domain.mapper.UserMapper;
import com.sginabreda.minesweeper.domain.usecase.CreateUser;
import com.sginabreda.minesweeper.domain.usecase.GenerateToken;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/users",
                produces = {"application/json"})
public class UserResource implements UserController {

	private final CreateUser createUser;
	private final GenerateToken generateToken;
	private final UserMapper userMapper;

	public UserResource(CreateUser createUser, GenerateToken generateToken, UserMapper userMapper) {
		this.createUser = createUser;
		this.generateToken = generateToken;
		this.userMapper = userMapper;
	}

	@Override
	@PostMapping(consumes = {"application/json"})
	@ResponseStatus(HttpStatus.CREATED)
	public UserDto createUser(@RequestBody UserRequestDto newUser) throws RequestException {
		return userMapper.toDto(createUser.invoke(newUser));
	}

	@Override
	@PostMapping(value = "/token", consumes = {"application/json"})
	@ResponseStatus(HttpStatus.OK)
	public JwtTokenDto generateToken(@RequestBody UserRequestDto user) throws RequestException {
		return generateToken.invoke(user);
	}
}
