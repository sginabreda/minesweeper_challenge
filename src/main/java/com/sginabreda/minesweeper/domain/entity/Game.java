package com.sginabreda.minesweeper.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Game {

	private Long id;
	private Integer rows;
	private Integer cols;
	private Integer mines;
	private Integer revealedMines;
	private List<Cell> cells;

	public Game(Integer rows, Integer cols, Integer mines, List<Cell> cells) {
		this.rows = rows;
		this.cols = cols;
		this.mines = mines;
		this.cells = cells;
	}
}
