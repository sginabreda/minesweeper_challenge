package com.sginabreda.minesweeper.unit.domain.usecase;

import com.sginabreda.minesweeper.domain.entity.Cell;
import com.sginabreda.minesweeper.domain.enums.Status;
import com.sginabreda.minesweeper.domain.exception.RequestException;
import com.sginabreda.minesweeper.domain.mapper.CellMapper;
import com.sginabreda.minesweeper.domain.usecase.ListCells;
import com.sginabreda.minesweeper.infrastructure.repository.CellRepository;
import com.sginabreda.minesweeper.infrastructure.repository.GameRepository;
import com.sginabreda.minesweeper.infrastructure.repository.model.CellModel;
import com.sginabreda.minesweeper.infrastructure.repository.model.GameModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ListCellsTest {

	private GameRepository gameRepository;
	private ListCells listCells;

	private GameModel gameModel;
	private final Long gameId = 1L;

	@BeforeEach
	void setUp() {
		gameRepository = mock(GameRepository.class);
		CellMapper cellMapper = new CellMapper();
		listCells = new ListCells(gameRepository, cellMapper);
	}

	@Test
	void testListCells() throws RequestException {
		// Given
		givenGameModel();

		// When
		when(gameRepository.findById(gameId)).thenReturn(Optional.ofNullable(gameModel));
		List<Cell> cells = listCells.invoke(gameId);

		// Then
		assertNotNull(cells);
		assertEquals(1, cells.size());
	}

	@Test
	void testListCells_gameNotExists_shouldThrowException() {
		// When
		when(gameRepository.findById(gameId)).thenReturn(Optional.empty());

		// Then
		assertThrows(RequestException.class, () -> listCells.invoke(gameId));
	}

	private void givenGameModel() {
		gameModel = new GameModel(1L, 5, 5, 5, 0,
				List.of(new CellModel(1L, 1, 1, Status.UNCLICKED.name(), 0, false, gameModel)));
	}
}
