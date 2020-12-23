package com.sginabreda.minesweeper.domain.usecase;

import com.sginabreda.minesweeper.delivery.dto.request.GameRequest;
import com.sginabreda.minesweeper.delivery.dto.response.GameDto;
import com.sginabreda.minesweeper.domain.entity.Cell;
import com.sginabreda.minesweeper.domain.entity.Game;
import com.sginabreda.minesweeper.domain.enums.Status;
import com.sginabreda.minesweeper.domain.exception.BadRequestException;

import java.util.ArrayList;
import java.util.List;

public class CreateGame {

	public Game execute(GameRequest gameRequest) throws BadRequestException {
		validateGame(gameRequest);
		Game newGame = generateGame(gameRequest);
		return newGame;
	}

	private void validateGame(GameRequest game) throws BadRequestException {
		Integer totalCellAmount = game.getCols() * game.getRows();

		if (game.getMines() > totalCellAmount) {
			throw new BadRequestException("Amount of mines is greater than amount of cells");
		}
	}

	private Game generateGame(GameRequest game) {
		List<Cell> cells = new ArrayList<>();
		Long id = 1L;

		for (int row = 1; row <= game.getRows(); row++) {
			for (int col = 1; col <= game.getCols(); col++) {
				cells.add(new Cell(id, row, col, Status.UNCLICKED, 0));
				id++;
			}
		}

		return new Game(game.getRows(), game.getCols(), game.getMines(), cells);
	}
}
