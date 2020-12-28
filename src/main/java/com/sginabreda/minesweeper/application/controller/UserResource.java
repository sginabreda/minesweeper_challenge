package com.sginabreda.minesweeper.application.controller;

import com.sginabreda.minesweeper.delivery.UserController;
import com.sginabreda.minesweeper.delivery.dto.request.UserRequestDto;
import com.sginabreda.minesweeper.delivery.dto.response.JwtTokenDto;
import com.sginabreda.minesweeper.delivery.dto.response.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users",
                produces = {"application/json"})
public class UserResource implements UserController {

	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UserDto createUser(UserRequestDto newUser) {
		return null;
	}

	@Override
	@PostMapping(name = "/token")
	@ResponseStatus(HttpStatus.OK)
	public JwtTokenDto login(UserRequestDto user) {
		return null;
	}
}
