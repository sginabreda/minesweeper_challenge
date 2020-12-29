package com.sginabreda.minesweeper.unit.domain.usecase;

import com.sginabreda.minesweeper.delivery.dto.request.UserRequestDto;
import com.sginabreda.minesweeper.domain.entity.User;
import com.sginabreda.minesweeper.domain.exception.RequestException;
import com.sginabreda.minesweeper.domain.mapper.UserMapper;
import com.sginabreda.minesweeper.domain.usecase.CreateUser;
import com.sginabreda.minesweeper.infrastructure.repository.model.UserModel;
import com.sginabreda.minesweeper.infrastructure.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CreateUserTest {

	private UserService userService;
	private CreateUser createUser;

	private UserRequestDto userRequestDto;
	private UserModel userModel;

	@BeforeEach
	void setUp() {
		userService = mock(UserService.class);
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		UserMapper userMapper = new UserMapper(passwordEncoder);
		createUser = new CreateUser(userService, userMapper);
	}

	@Test
	void testCreateUser() throws RequestException {
		// Given
		givenUserRequest();
		givenUserModel();

		// When
		when(userService.save(any())).thenReturn(userModel);
		User user = createUser.invoke(userRequestDto);

		// Then
		assertNotNull(user);
	}

	private void givenUserRequest() {
		userRequestDto = new UserRequestDto("santiago.ginabreda", "password");
	}

	private void givenUserModel() {
		userModel = new UserModel(1L, "santiago.ginabreda", "password");
	}
}
