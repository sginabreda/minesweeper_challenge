package com.sginabreda.minesweeper.unit.domain.usecase;

import com.sginabreda.minesweeper.domain.entity.Game;
import com.sginabreda.minesweeper.domain.exception.GameNotFoundException;
import com.sginabreda.minesweeper.domain.mapper.CellMapper;
import com.sginabreda.minesweeper.domain.mapper.GameMapper;
import com.sginabreda.minesweeper.domain.usecase.GetGame;
import com.sginabreda.minesweeper.infrastructure.repository.GameRepository;
import com.sginabreda.minesweeper.infrastructure.repository.model.GameModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class GetGameTest {

	private GameRepository gameRepository;
	private GetGame getGame;

	private GameModel gameModel;
	private Long gameId = 1L;

	@BeforeEach
	void setUp() {
		gameRepository = mock(GameRepository.class);
		CellMapper cellMapper = new CellMapper();
		GameMapper gameMapper = new GameMapper(cellMapper);
		getGame = new GetGame(gameRepository, gameMapper);
	}

	@Test
	void testGetGame() throws GameNotFoundException {
		// Given
		givenGameModel();

		// When
		when(gameRepository.findById(gameId)).thenReturn(Optional.ofNullable(gameModel));
		Game game = getGame.invoke(gameId);

		// Then
		verify(gameRepository).findById(gameId);
		assertNotNull(game);
	}

	@Test
	void testGetGame_gameNotExists_shouldThrowException() {
		// When
		when(gameRepository.findById(gameId)).thenReturn(Optional.empty());

		// Then
		assertThrows(GameNotFoundException.class, () -> getGame.invoke(gameId));
	}

	private void givenGameModel() {
		gameModel = new GameModel(1L, 5, 5, 5, 0, new ArrayList<>());
	}
}
