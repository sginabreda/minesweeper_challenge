package com.sginabreda.minesweeper.domain.entity;

import com.sginabreda.minesweeper.delivery.dto.response.GameDto;
import com.sginabreda.minesweeper.infrastructure.repository.model.GameModel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter
@AllArgsConstructor
public class Game {

	private Long id;
	private Integer rows;
	private Integer cols;
	private Integer mines;
	private List<Cell> cells;

	public GameDto toDto() {
		return new GameDto(id, rows, cols, mines);
	}

	public GameModel toModel() {
		GameModel game = new GameModel(id, rows, cols, mines);
		game.setCells(cells.stream().map(cell -> cell.toModel(game)).collect(toList()));
		return game;
	}

	public Game(Integer rows, Integer cols, Integer mines, List<Cell> cells) {
		this.rows = rows;
		this.cols = cols;
		this.mines = mines;
		this.cells = cells;
	}
}
