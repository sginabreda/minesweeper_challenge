package com.sginabreda.minesweeper.unit.domain.usecase;

import com.sginabreda.minesweeper.delivery.dto.request.UserRequestDto;
import com.sginabreda.minesweeper.delivery.dto.response.JwtTokenDto;
import com.sginabreda.minesweeper.domain.mapper.UserMapper;
import com.sginabreda.minesweeper.domain.usecase.GenerateToken;
import com.sginabreda.minesweeper.infrastructure.service.UserService;
import com.sginabreda.minesweeper.util.JwtTokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

public class GenerateTokenTest {

	private UserService userService;
	private UserMapper userMapper;
	private JwtTokenUtil jwtTokenUtil;
	private GenerateToken generateToken;

	private UserRequestDto userRequestDto;
	private UserDetails userDetails;

	@BeforeEach
	public void setUp() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		userMapper = new UserMapper(encoder);
		userService = Mockito.mock(UserService.class);
		jwtTokenUtil = Mockito.mock(JwtTokenUtil.class);
		generateToken = new GenerateToken(userService, userMapper, jwtTokenUtil);
	}

	@Test
	void testGenerateToken() {
		// Given
		givenUserRequest();
		givenUserDetails();

		// When
		Mockito.when(userService.loadUserByUsername(userRequestDto.getUsername())).thenReturn(userDetails);
		JwtTokenDto jwtTokenDto = generateToken.invoke(userRequestDto);

		// Then
		assertNotNull(jwtTokenDto);
		verify(userService).loadUserByUsername(anyString());
	}

	private void givenUserRequest() {
		userRequestDto = new UserRequestDto("santiago.ginabreda", "password");
	}

	private void givenUserDetails() {
		userDetails = new User("santiago.ginabreda", "password", new ArrayList<>());
	}
}
