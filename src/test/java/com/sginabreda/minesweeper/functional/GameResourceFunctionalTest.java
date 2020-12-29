package com.sginabreda.minesweeper.functional;

import com.sginabreda.minesweeper.delivery.dto.ApiError;
import com.sginabreda.minesweeper.delivery.dto.request.GameRequest;
import com.sginabreda.minesweeper.delivery.dto.request.UserRequestDto;
import com.sginabreda.minesweeper.delivery.dto.response.CellDto;
import com.sginabreda.minesweeper.delivery.dto.response.GameDto;
import com.sginabreda.minesweeper.delivery.dto.response.JwtTokenDto;
import com.sginabreda.minesweeper.delivery.dto.response.UserDto;
import com.sginabreda.minesweeper.infrastructure.repository.CellRepository;
import com.sginabreda.minesweeper.infrastructure.repository.GameRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GameResourceFunctionalTest extends FunctionalTest {

	@Autowired
	private GameRepository gameRepository;
	@Autowired
	private CellRepository cellRepository;
	private String controller;
	private String jwt;
	private HttpHeaders headers;
	private GameDto gameDto;

	@BeforeEach
	void setUp() {
		controller = testRestTemplate.getRootUri() + "/games";
	}

	@AfterEach
	void afterEach() {
		cellRepository.deleteAll();
		gameRepository.deleteAll();
	}

	@Test
	void testCreateGame_nominalCase_returnsOk() {
		// Given
		givenUserCreated();
		GameRequest gameRequest = new GameRequest(8, 8, 10);
		HttpEntity httpEntity = new HttpEntity(gameRequest, headers);

		// When
		ResponseEntity<GameDto> newGame = testRestTemplate.exchange(controller, HttpMethod.POST, httpEntity,
				GameDto.class);

		// Then
		assertNotNull(newGame);
		assertEquals(HttpStatus.CREATED, newGame.getStatusCode());
	}

	@Test
	void testCreateGame_moreMinesThanCells_shouldReturnBadRequest() {
		// Given
		givenUserCreated();
		GameRequest gameRequest = new GameRequest(5, 5, 30);
		HttpEntity httpEntity = new HttpEntity(gameRequest, headers);

		// When
		ResponseEntity<ApiError> apiError = testRestTemplate.exchange(controller, HttpMethod.POST, httpEntity,
				ApiError.class);

		// Then
		assertNotNull(apiError);
		assertEquals(HttpStatus.BAD_REQUEST, apiError.getStatusCode());
	}

	@Test
	void testListGames_nominalCase_returnsGamesList() {
		// Given
		givenUserCreated();
		givenGameCreated();
		HttpEntity entity = new HttpEntity(headers);
		ParameterizedTypeReference<List<GameDto>> type = new ParameterizedTypeReference<>() {};

		// When
		ResponseEntity<List<GameDto>> gameList = testRestTemplate.exchange(controller, HttpMethod.GET, entity, type);

		// Then
		assertNotNull(gameList);
		assertEquals(HttpStatus.OK, gameList.getStatusCode());
		assertEquals(1, gameList.getBody().size());
	}

	@Test
	void testGetGame_nominalCase_returnsGameOk() {
		// Given
		givenUserCreated();
		givenGameCreated();
		HttpEntity entity = new HttpEntity(headers);
		String gameUrl = controller + "/" + gameDto.getId();

		// When
		ResponseEntity<GameDto> game = testRestTemplate.exchange(gameUrl, HttpMethod.GET, entity, GameDto.class);

		// Then
		assertNotNull(game);
		assertEquals(HttpStatus.OK, game.getStatusCode());
	}

	@Test
	void testGetGame_gameNotExists_returnNotFound() {
		// Given
		givenUserCreated();
		HttpEntity entity = new HttpEntity(headers);
		String gameUrl = controller + "/30";

		// When
		ResponseEntity<ApiError> errorResponse = testRestTemplate.exchange(gameUrl, HttpMethod.GET, entity, ApiError.class);

		// Then
		assertNotNull(errorResponse);
		assertEquals(HttpStatus.NOT_FOUND, errorResponse.getStatusCode());
	}

	@Test
	void testListCells_nominalCase_returnsCellsList() {
		// Given
		givenUserCreated();
		givenGameCreated();
		HttpEntity entity = new HttpEntity(headers);
		String gameUrl = controller + "/" + gameDto.getId() + "/cells";
		ParameterizedTypeReference<List<CellDto>> type = new ParameterizedTypeReference<>() {};

		// When
		ResponseEntity<List<CellDto>> cellLists = testRestTemplate.exchange(gameUrl, HttpMethod.GET, entity, type);

		// Then
		assertNotNull(cellLists);
		assertEquals(64, cellLists.getBody().size());
	}

	private void givenUserCreated() {
		String usersUrl = testRestTemplate.getRootUri() + "/users";
		UserRequestDto userRequestDto = new UserRequestDto("santi.ginabreda@gmail.com", "password");
		HttpEntity httpEntity = new HttpEntity(userRequestDto);
		testRestTemplate.exchange(usersUrl, HttpMethod.POST, httpEntity, UserDto.class);

		ResponseEntity<JwtTokenDto> jwtToken = testRestTemplate.exchange(usersUrl + "/token", HttpMethod.POST,
				httpEntity, JwtTokenDto.class);

		jwt = jwtToken.getBody().getToken();
		headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + jwt);
	}

	private void givenGameCreated() {
		GameRequest gameRequest = new GameRequest(8, 8, 10);
		HttpEntity httpEntity = new HttpEntity(gameRequest, headers);

		gameDto = testRestTemplate.exchange(controller, HttpMethod.POST, httpEntity, GameDto.class).getBody();
	}
}
