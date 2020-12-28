package com.sginabreda.minesweeper.delivery;

import com.sginabreda.minesweeper.delivery.dto.request.UserRequestDto;
import com.sginabreda.minesweeper.delivery.dto.response.JwtTokenDto;
import com.sginabreda.minesweeper.delivery.dto.response.UserDto;

public interface UserController {

	UserDto createUser(UserRequestDto newUser);

	JwtTokenDto generateToken(UserRequestDto user);
}
