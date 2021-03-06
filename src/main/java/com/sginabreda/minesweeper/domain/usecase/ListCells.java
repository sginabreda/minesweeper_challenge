package com.sginabreda.minesweeper.domain.usecase;

import com.sginabreda.minesweeper.domain.entity.Cell;
import com.sginabreda.minesweeper.domain.exception.RequestException;
import com.sginabreda.minesweeper.domain.mapper.CellMapper;
import com.sginabreda.minesweeper.infrastructure.repository.GameRepository;
import com.sginabreda.minesweeper.infrastructure.repository.model.GameModel;
import org.springframework.http.HttpStatus;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class ListCells {

	private final GameRepository gameRepository;
	private final CellMapper cellMapper;

	public List<Cell> invoke(Long gameId) throws RequestException {
		GameModel game = gameRepository.findById(gameId).orElseThrow(
				() -> new RequestException("Game not found", "not.found", HttpStatus.NOT_FOUND.value()));

		return game.getCells().stream().map(cellMapper::toDomain).collect(toList());
	}

	public ListCells(GameRepository gameRepository, CellMapper cellMapper) {
		this.gameRepository = gameRepository;
		this.cellMapper = cellMapper;
	}
}
