package com.sginabreda.minesweeper.domain.usecase;

import com.sginabreda.minesweeper.domain.entity.Cell;
import com.sginabreda.minesweeper.domain.exception.GameNotFoundException;
import com.sginabreda.minesweeper.domain.mapper.CellMapper;
import com.sginabreda.minesweeper.infrastructure.repository.CellRepository;
import com.sginabreda.minesweeper.infrastructure.repository.GameRepository;
import com.sginabreda.minesweeper.infrastructure.repository.model.GameModel;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class ListCells {

	private final GameRepository gameRepository;
	private final CellRepository cellRepository;
	private final CellMapper cellMapper;

	public List<Cell> invoke(Long gameId) throws GameNotFoundException {
		Optional<GameModel> game = gameRepository.findById(gameId);
		if (game.isEmpty()) {
			throw new GameNotFoundException();
		}
		return cellRepository.findAllByGame(game.get()).stream().map(cellMapper::toDomain).collect(toList());
	}

	public ListCells(GameRepository gameRepository, CellRepository cellRepository, CellMapper cellMapper) {
		this.cellRepository = cellRepository;
		this.gameRepository = gameRepository;
		this.cellMapper = cellMapper;
	}
}
