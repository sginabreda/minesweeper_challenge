package com.sginabreda.minesweeper.functional;

import com.sginabreda.minesweeper.delivery.dto.ApiError;
import com.sginabreda.minesweeper.delivery.dto.request.UserRequestDto;
import com.sginabreda.minesweeper.delivery.dto.response.JwtTokenDto;
import com.sginabreda.minesweeper.delivery.dto.response.UserDto;
import com.sginabreda.minesweeper.infrastructure.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserResourceFunctionalTest extends FunctionalTest {

	@Autowired
	private UserRepository userRepository;
	private String controller;

	@BeforeEach
	void setUp() {
		controller = testRestTemplate.getRootUri() + "/users";
	}

	@AfterEach
	void afterEach() {
		userRepository.deleteAll();
	}

	@Test
	void testCreateUser_nominalCase_createsUserOk() {
		// Given
		UserRequestDto userRequestDto = new UserRequestDto("santi.ginabreda@gmail.com", "password");
		HttpEntity httpEntity = new HttpEntity(userRequestDto);

		// When
		ResponseEntity<UserDto> newUser = testRestTemplate.exchange(controller, HttpMethod.POST, httpEntity,
				UserDto.class);

		// Then
		assertNotNull(newUser);
		assertEquals(HttpStatus.CREATED, newUser.getStatusCode());
	}

	@Test
	void testCreateUser_userAlreadyExists_throwsConflict() {
		// Given
		UserRequestDto userRequestDto = new UserRequestDto("santi.ginabreda@gmail.com", "password");
		HttpEntity httpEntity = new HttpEntity(userRequestDto);
		testRestTemplate.exchange(controller, HttpMethod.POST, httpEntity, UserDto.class);

		// When
		ResponseEntity<ApiError> errorResponse = testRestTemplate.exchange(controller, HttpMethod.POST, httpEntity,
				ApiError.class);

		// Then
		assertNotNull(errorResponse);
		assertEquals(HttpStatus.CONFLICT, errorResponse.getStatusCode());
		assertEquals("User already exists", errorResponse.getBody().getMessage());
		assertEquals("user.conflict", errorResponse.getBody().getCode());
	}

	@Test
	void testGenerateToken_nominalCase_returnsJwtToken() {
		// Given
		UserRequestDto userRequestDto = new UserRequestDto("santi.ginabreda@gmail.com", "password");
		HttpEntity httpEntity = new HttpEntity(userRequestDto);
		String tokenUrl = controller + "/token";
		testRestTemplate.exchange(controller, HttpMethod.POST, httpEntity, UserDto.class);

		// When
		ResponseEntity<JwtTokenDto> jwtToken = testRestTemplate.exchange(tokenUrl, HttpMethod.POST, httpEntity,
				JwtTokenDto.class);

		// Then
		assertNotNull(jwtToken);
		assertEquals(HttpStatus.OK, jwtToken.getStatusCode());
	}

	@Test
	void testGenerateToken_wrongUsername_throwsNotFound() {
		// Given
		String tokenUrl = controller + "/token";
		UserRequestDto newUser = new UserRequestDto("santi.ginabreda@gmail.com", "password");
		UserRequestDto wrongUsername = new UserRequestDto("santi.ginabreda", "password");
		HttpEntity httpEntity = new HttpEntity(newUser);
		HttpEntity httpEntity2 = new HttpEntity(wrongUsername);
		testRestTemplate.exchange(controller, HttpMethod.POST, httpEntity, UserDto.class);

		// When
		ResponseEntity<ApiError> errorResponse = testRestTemplate.exchange(tokenUrl, HttpMethod.POST, httpEntity2,
				ApiError.class);

		// Then
		assertNotNull(errorResponse);
		assertEquals(HttpStatus.NOT_FOUND, errorResponse.getStatusCode());
		assertEquals("Username not found", errorResponse.getBody().getMessage());
		assertEquals("username.not.found", errorResponse.getBody().getCode());
	}
}
