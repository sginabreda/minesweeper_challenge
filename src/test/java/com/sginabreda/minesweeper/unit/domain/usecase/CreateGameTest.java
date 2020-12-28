package com.sginabreda.minesweeper.unit.domain.usecase;

import com.sginabreda.minesweeper.delivery.dto.request.GameRequest;
import com.sginabreda.minesweeper.domain.entity.Game;
import com.sginabreda.minesweeper.domain.mapper.CellMapper;
import com.sginabreda.minesweeper.domain.mapper.GameMapper;
import com.sginabreda.minesweeper.domain.usecase.CreateGame;
import com.sginabreda.minesweeper.infrastructure.repository.CellRepository;
import com.sginabreda.minesweeper.infrastructure.repository.GameRepository;
import com.sginabreda.minesweeper.infrastructure.repository.model.GameModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CreateGameTest {

	private GameRepository gameRepository;
	private CreateGame createGame;

	private GameRequest gameRequest;
	private GameModel gameModel;

	@BeforeEach
	public void setUp() {
		gameRepository = mock(GameRepository.class);
		CellRepository cellRepository = mock(CellRepository.class);
		CellMapper cellMapper = new CellMapper();
		GameMapper gameMapper = new GameMapper(cellMapper);
		createGame = new CreateGame(gameRepository, cellRepository, gameMapper, cellMapper);
	}

	@Test
	void testCreateGame() {
		// Given
		givenGameRequest();
		givenGameModel();

		// When
		when(gameRepository.save(Mockito.any(GameModel.class))).thenReturn(gameModel);
		Game game = createGame.invoke(gameRequest);

		// Then
		assertNotNull(game);
		assertEquals(gameRequest.getRows(), game.getRows());
		assertEquals(gameRequest.getCols(), game.getCols());
		assertEquals(gameRequest.getMines(), game.getMines());
	}

	private void givenGameRequest() {
		gameRequest = new GameRequest(5, 5, 5);
	}

	private void givenGameModel() {
		gameModel = new GameModel(1L, 5, 5, 5, 0, new ArrayList<>());
	}
}
