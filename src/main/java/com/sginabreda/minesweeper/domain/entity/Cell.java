package com.sginabreda.minesweeper.domain.entity;

import com.sginabreda.minesweeper.delivery.dto.response.CellDto;
import com.sginabreda.minesweeper.domain.enums.Status;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Cell {

	private Long id;
	private Integer row;
	private Integer col;
	private Status status;
	private Integer adjacentMines;

	public CellDto toDto() {
		return new CellDto(id, row, col, status.name().toLowerCase(), adjacentMines);
	}
}
