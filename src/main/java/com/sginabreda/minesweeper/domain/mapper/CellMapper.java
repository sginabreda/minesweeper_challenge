package com.sginabreda.minesweeper.domain.mapper;

import com.sginabreda.minesweeper.delivery.dto.response.CellDto;
import com.sginabreda.minesweeper.domain.entity.Cell;
import com.sginabreda.minesweeper.domain.enums.Status;
import com.sginabreda.minesweeper.infrastructure.repository.model.CellModel;
import com.sginabreda.minesweeper.infrastructure.repository.model.GameModel;

public class CellMapper {

	public CellModel toModel(Cell cell, GameModel gameModel) {
		CellModel cellModel = new CellModel();
		cellModel.setCellRow(cell.getRow());
		cellModel.setCellCol(cell.getCol());
		cellModel.setCellStatus(cell.getStatus().name());
		cellModel.setAdjacentMines(cell.getAdjacentMines());
		cellModel.setGame(gameModel);

		return cellModel;
	}

	public Cell toDomain(CellModel cellModel) {
		Cell cell = new Cell();
		cell.setId(cellModel.getId());
		cell.setCol(cellModel.getCellCol());
		cell.setRow(cellModel.getCellRow());
		cell.setStatus(Status.valueOf(cellModel.getCellStatus()));
		cell.setAdjacentMines(cellModel.getAdjacentMines());

		return cell;
	}

	public CellDto toDto(Cell cell) {
		CellDto cellDto = new CellDto();
		cellDto.setId(cell.getId());
		cellDto.setCol(cell.getCol());
		cellDto.setRow(cell.getRow());
		cellDto.setAdjacentMines(cell.getAdjacentMines());
		cellDto.setStatus(cell.getStatus().name());

		return cellDto;
	}
}
