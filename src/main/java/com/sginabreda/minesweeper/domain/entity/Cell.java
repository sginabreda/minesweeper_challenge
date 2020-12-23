package com.sginabreda.minesweeper.domain.entity;

import com.sginabreda.minesweeper.delivery.dto.response.CellDto;
import com.sginabreda.minesweeper.domain.enums.Status;
import com.sginabreda.minesweeper.infrastructure.repository.model.CellModel;
import com.sginabreda.minesweeper.infrastructure.repository.model.GameModel;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Cell {

	private Long id;
	private Integer row;
	private Integer col;
	private Status status;
	private Integer adjacentMines;

	public CellDto toDto() {
		return new CellDto(id, row, col, status.name(), adjacentMines);
	}

	public CellModel toModel(GameModel game) {
		return new CellModel(row, col, status.name(), adjacentMines, game);
	}

	public Cell(Integer row, Integer col, Status status, Integer adjacentMines) {
		this.row = row;
		this.col = col;
		this.status = status;
		this.adjacentMines = adjacentMines;
	}
}
