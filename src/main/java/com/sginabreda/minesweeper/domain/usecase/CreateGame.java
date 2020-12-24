package com.sginabreda.minesweeper.domain.usecase;

import com.sginabreda.minesweeper.delivery.dto.request.GameRequest;
import com.sginabreda.minesweeper.domain.entity.Cell;
import com.sginabreda.minesweeper.domain.entity.Game;
import com.sginabreda.minesweeper.domain.enums.Status;
import com.sginabreda.minesweeper.domain.exception.BadRequestException;
import com.sginabreda.minesweeper.infrastructure.repository.CellRepository;
import com.sginabreda.minesweeper.infrastructure.repository.GameRepository;
import com.sginabreda.minesweeper.infrastructure.repository.model.GameModel;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class CreateGame {

	private final GameRepository repository;
	private final CellRepository cellRepository;

	public Game invoke(GameRequest gameRequest) throws BadRequestException {
		validateGame(gameRequest);
		Game newGame = generateGame(gameRequest);
		GameModel gameModel = repository.save(newGame.toModel());
		cellRepository.saveAll(newGame.getCells().stream().map(cell -> cell.toModel(gameModel)).collect(toList()));
		return gameModel.toDomain();
	}

	private void validateGame(GameRequest game) throws BadRequestException {
		Integer totalCellAmount = game.getCols() * game.getRows();

		if (game.getMines() > totalCellAmount) {
			throw new BadRequestException("Amount of mines is greater than amount of cells");
		}
	}

	private Game generateGame(GameRequest game) {
		List<Cell> cells = new ArrayList<>();
		long id = 1L;

		for (int row = 1; row <= game.getRows(); row++) {
			for (int col = 1; col <= game.getCols(); col++) {
				cells.add(new Cell(row, col, Status.UNCLICKED, 0));
				id++;
			}
		}

		return new Game(game.getRows(), game.getCols(), game.getMines(), cells);
	}

	public CreateGame(GameRepository gameRepository, CellRepository cellRepository) {
		this.repository = gameRepository;
		this.cellRepository = cellRepository;
	}
}
