package com.sginabreda.minesweeper.domain.usecase;

import com.sginabreda.minesweeper.delivery.dto.request.CellStatusChangeRequest;
import com.sginabreda.minesweeper.delivery.dto.response.CellDto;
import com.sginabreda.minesweeper.domain.exception.GameNotFoundException;
import com.sginabreda.minesweeper.infrastructure.repository.CellRepository;
import com.sginabreda.minesweeper.infrastructure.repository.GameRepository;
import com.sginabreda.minesweeper.infrastructure.repository.model.GameModel;

import java.util.Optional;

import static com.sginabreda.minesweeper.domain.enums.Status.REVEALED;

public class ChangeCellStatus {

	private final GameRepository gameRepository;
	private final CellRepository cellRepository;

	public CellDto invoke(Long gameId, Long cellId, CellStatusChangeRequest newStatus) throws GameNotFoundException {
		Optional<GameModel> game = gameRepository.findById(gameId);
		if (game.isEmpty()) {
			throw new GameNotFoundException();
		}
		return new CellDto(1L, 1, 1, REVEALED.name(), null);
	}

	public ChangeCellStatus(GameRepository gameRepository, CellRepository cellRepository) {
		this.cellRepository = cellRepository;
		this.gameRepository = gameRepository;
	}
}
