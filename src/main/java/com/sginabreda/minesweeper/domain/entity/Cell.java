package com.sginabreda.minesweeper.domain.entity;

import com.sginabreda.minesweeper.domain.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cell {

	private Long id;
	private Integer row;
	private Integer col;
	private Status status;
	private Integer adjacentMines;

	public Cell(Integer row, Integer col, Status status, Integer adjacentMines) {
		this.row = row;
		this.col = col;
		this.status = status;
		this.adjacentMines = adjacentMines;
	}
}
