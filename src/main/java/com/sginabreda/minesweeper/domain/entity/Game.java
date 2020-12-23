package com.sginabreda.minesweeper.domain.entity;

import com.sginabreda.minesweeper.delivery.dto.response.GameDto;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class Game {

	private Integer rows;
	private Integer cols;
	private Integer mines;
	private List<Cell> cells;

	public Game(Integer rows, Integer cols, Integer mines) {
		this.rows = rows;
		this.cols = cols;
		this.mines = mines;
	}

	public GameDto toDto() {
		return new GameDto(this.rows, this.cols, this.mines,
				this.cells.stream().map(Cell::toDto).collect(Collectors.toList()));
	}
}
