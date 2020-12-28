package com.sginabreda.minesweeper.unit.domain.mapper;

import com.sginabreda.minesweeper.delivery.dto.response.CellDto;
import com.sginabreda.minesweeper.domain.entity.Cell;
import com.sginabreda.minesweeper.domain.enums.Status;
import com.sginabreda.minesweeper.domain.mapper.CellMapper;
import com.sginabreda.minesweeper.infrastructure.repository.model.CellModel;
import com.sginabreda.minesweeper.infrastructure.repository.model.GameModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CellMapperTest {

	private CellMapper cellMapper;

	@BeforeEach
	void setUp() {
		cellMapper = new CellMapper();
	}

	@Test
	void testToModel() {
		// Given
		Cell cell = new Cell(1L, 1, 1, Status.UNCLICKED, 1);
		GameModel gameModel = new GameModel(1L, 1, 1, 1, 0, new ArrayList<>());

		// When
		CellModel cellModel = cellMapper.toModel(cell, gameModel);

		// Then
		assertNotNull(cellModel);
		assertEquals(cell.getId(), cellModel.getId());
		assertEquals(cell.getCol(), cellModel.getCellCol());
		assertEquals(cell.getRow(), cellModel.getCellRow());
		assertEquals(cell.getAdjacentMines(), cellModel.getAdjacentMines());
		assertEquals(cell.getStatus().name(), cellModel.getCellStatus());
	}

	@Test
	void testToDomain() {
		// Given
		CellModel cellModel = new CellModel(1L, 1, 1, Status.UNCLICKED.name(), 0, false, new GameModel());

		// When
		Cell cell = cellMapper.toDomain(cellModel);

		// Then
		assertNotNull(cell);
		assertEquals(cellModel.getId(), cell.getId());
		assertEquals(cellModel.getAdjacentMines(), cell.getAdjacentMines());
		assertEquals(cellModel.getCellRow(), cell.getRow());
		assertEquals(cellModel.getCellCol(), cell.getCol());
		assertEquals(Status.valueOf(cellModel.getCellStatus()), cell.getStatus());
	}

	@Test
	void testToDto() {
		// Given
		Cell cell = new Cell(1L, 1, 1, Status.UNCLICKED, 1);

		// When
		CellDto cellDto = cellMapper.toDto(cell);

		// Then
		assertNotNull(cellDto);
		assertEquals(cell.getStatus().name(), cellDto.getStatus());
		assertEquals(cell.getId(), cellDto.getId());
		assertEquals(cell.getRow(), cellDto.getCol());
		assertEquals(cell.getRow(), cellDto.getRow());
		assertEquals(cell.getAdjacentMines(), cellDto.getAdjacentMines());
	}
}
