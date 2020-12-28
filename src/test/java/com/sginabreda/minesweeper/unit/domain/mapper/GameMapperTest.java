package com.sginabreda.minesweeper.unit.domain.mapper;

import com.sginabreda.minesweeper.delivery.dto.response.GameDto;
import com.sginabreda.minesweeper.domain.entity.Game;
import com.sginabreda.minesweeper.domain.mapper.CellMapper;
import com.sginabreda.minesweeper.domain.mapper.GameMapper;
import com.sginabreda.minesweeper.infrastructure.repository.model.GameModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GameMapperTest {

	private GameMapper gameMapper;
	private CellMapper cellMapper;

	@BeforeEach
	void setUp() {
		cellMapper = new CellMapper();
		gameMapper = new GameMapper(cellMapper);
	}

	@Test
	void testToDomain() {
		// Given
		GameModel gameModel = new GameModel(1L, 1, 1, 1, 0, new ArrayList<>());

		// When
		Game game = gameMapper.toDomain(gameModel);

		// Then
		assertNotNull(game);
		assertEquals(gameModel.getId(), game.getId());
		assertEquals(gameModel.getCols(), game.getCols());
		assertEquals(gameModel.getRows(), game.getRows());
		assertEquals(gameModel.getRevealedMines(), game.getRevealedMines());
		assertEquals(gameModel.getMines(), gameModel.getMines());
		assertEquals(gameModel.getCells().size(), game.getCells().size());
	}

	@Test
	void testToModel() {
		// Given
		Game game = new Game(1L, 1, 1, 1, 0, new ArrayList<>());

		// When
		GameModel gameModel = gameMapper.toModel(game);

		// Then
		assertNotNull(gameModel);
		assertEquals(game.getId(), gameModel.getId());
		assertEquals(game.getCols(), gameModel.getCols());
		assertEquals(game.getRows(), gameModel.getRows());
		assertEquals(game.getRevealedMines(), gameModel.getRevealedMines());
		assertEquals(game.getMines(), gameModel.getMines());
		assertEquals(game.getCells().size(), gameModel.getCells().size());
	}

	@Test
	void testToDto() {
		// Given
		Game game = new Game(1L, 1, 1, 1, 0, new ArrayList<>());

		// When
		GameDto gameDto = gameMapper.toDto(game);

		// Then
		assertNotNull(gameDto);
		assertEquals(game.getId(), gameDto.getId());
		assertEquals(game.getCols(), gameDto.getCols());
		assertEquals(game.getRows(), gameDto.getRows());
		assertEquals(game.getRevealedMines(), gameDto.getRevealedMines());
		assertEquals(game.getMines(), gameDto.getMines());
	}
}
