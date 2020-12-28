package com.sginabreda.minesweeper.unit.domain.mapper;

import com.sginabreda.minesweeper.delivery.dto.response.UserDto;
import com.sginabreda.minesweeper.domain.entity.User;
import com.sginabreda.minesweeper.domain.mapper.UserMapper;
import com.sginabreda.minesweeper.infrastructure.repository.model.UserModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

public class UserMapperTest {

	private UserMapper userMapper;
	private PasswordEncoder passwordEncoder;

	@BeforeEach
	void setUp() {
		passwordEncoder = new BCryptPasswordEncoder();
		userMapper = new UserMapper(passwordEncoder);
	}

	@Test
	void testToDto() {
		// Given
		User user = new User(1L, "santiago.ginabreda", "password");

		// When
		UserDto userDto = userMapper.toDto(user);

		// Then
		assertNotNull(userDto);
		assertEquals(user.getId(), userDto.getId());
		assertEquals(user.getUsername(), userDto.getUsername());
		assertEquals(user.getPassword(), userDto.getPassword());
	}

	@Test
	void testToModel() {
		// Given
		User user = new User(1L, "santiago.ginabreda", "password");

		// When
		UserModel userModel = userMapper.toModel(user);

		// Then
		assertNotNull(userModel);
		assertEquals(userModel.getUsername(), user.getUsername());
		assertNotEquals(userModel.getPassword(), user.getPassword());
	}

	@Test
	void testModelToDomain() {
		// Given
		UserModel userModel = new UserModel(1L, "santiago.ginabreda", "password");

		// When
		User user = userMapper.toDomain(userModel);

		// Then
		assertNotNull(user);
		assertEquals(userModel.getId(), user.getId());
		assertEquals(userModel.getPassword(), user.getPassword());
		assertEquals(userModel.getUsername(), user.getUsername());
	}
}
