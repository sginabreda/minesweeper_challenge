package com.sginabreda.minesweeper.domain.usecase;

import com.sginabreda.minesweeper.delivery.dto.request.CellStatusChangeRequest;
import com.sginabreda.minesweeper.domain.entity.Cell;
import com.sginabreda.minesweeper.domain.enums.Status;
import com.sginabreda.minesweeper.domain.exception.CellNotFoundException;
import com.sginabreda.minesweeper.domain.exception.GameNotFoundException;
import com.sginabreda.minesweeper.domain.exception.RevealedCellException;
import com.sginabreda.minesweeper.infrastructure.repository.CellRepository;
import com.sginabreda.minesweeper.infrastructure.repository.GameRepository;
import com.sginabreda.minesweeper.infrastructure.repository.model.CellModel;
import com.sginabreda.minesweeper.infrastructure.repository.model.GameModel;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class ChangeCellStatus {

	private final GameRepository gameRepository;
	private final CellRepository cellRepository;

	public ChangeCellStatus(GameRepository gameRepository, CellRepository cellRepository) {
		this.cellRepository = cellRepository;
		this.gameRepository = gameRepository;
	}

	public Cell invoke(Long gameId, Long cellId, CellStatusChangeRequest changeRequest) throws GameNotFoundException, CellNotFoundException, RevealedCellException {
		Optional<GameModel> game = gameRepository.findById(gameId);
		Optional<CellModel> cell = cellRepository.findById(cellId);
		Status status = Status.valueOf(changeRequest.getStatus().toUpperCase());
		if (game.isEmpty()) {
			throw new GameNotFoundException();
		} else if (cell.isEmpty()) {
			throw new CellNotFoundException();
		}
		if (! game.get().isStarted()) {
			generateMines(game.get(), cellId);
		}
		changeCellStatus(cell.get(), status);
		return new Cell(1, 1, Status.UNCLICKED, 1);
	}

	private void generateMines(GameModel gameModel, Long firstCell) {
		List<CellModel> cells = gameModel.getCells();
		Integer minesAmount = gameModel.getMines();
		int minesCreated = 0;
		while (minesCreated < minesAmount) {
			CellModel mine = cells.get(new Random().nextInt(cells.size()));
			if (! mine.getId().equals(firstCell) && ! mine.isHasMine()) {
				mine.setHasMine(true);
				minesCreated++;
			}
		}
		cellRepository.saveAll(cells);
	}

	private void changeCellStatus(CellModel cell, Status status) throws RevealedCellException {
		if (cell.getCellStatus() == Status.REVEALED.name()) {
			throw new RevealedCellException();
		}
	}

	private void updateGameAndCell() {

	}
}
