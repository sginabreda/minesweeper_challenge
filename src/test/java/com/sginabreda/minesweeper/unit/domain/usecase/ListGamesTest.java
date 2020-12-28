package com.sginabreda.minesweeper.unit.domain.usecase;

import com.sginabreda.minesweeper.domain.entity.Game;
import com.sginabreda.minesweeper.domain.mapper.CellMapper;
import com.sginabreda.minesweeper.domain.mapper.GameMapper;
import com.sginabreda.minesweeper.domain.usecase.ListGames;
import com.sginabreda.minesweeper.infrastructure.repository.GameRepository;
import com.sginabreda.minesweeper.infrastructure.repository.model.GameModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class ListGamesTest {

	private GameRepository gameRepository;
	private ListGames listGames;

	private GameModel gameModel;

	@BeforeEach
	void setUp() {
		gameRepository = mock(GameRepository.class);
		CellMapper cellMapper = new CellMapper();
		GameMapper gameMapper = new GameMapper(cellMapper);
		listGames = new ListGames(gameRepository, gameMapper);
	}

	@Test
	void testListGames() {
		// Given
		givenGameModel();

		// When
		when(gameRepository.findAll()).thenReturn(Collections.singletonList(gameModel));
		List<Game> games = listGames.invoke();

		// Then
		assertNotNull(games);
		assertEquals(1, games.size());
		verify(gameRepository).findAll();
	}

	private void givenGameModel() {
		gameModel = new GameModel(1L, 5, 5, 5, 0, new ArrayList<>());
	}
}
