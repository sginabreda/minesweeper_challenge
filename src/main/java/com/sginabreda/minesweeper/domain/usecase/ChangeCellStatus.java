package com.sginabreda.minesweeper.domain.usecase;

import com.sginabreda.minesweeper.delivery.dto.request.CellStatusChangeRequest;
import com.sginabreda.minesweeper.domain.entity.Cell;
import com.sginabreda.minesweeper.domain.enums.Status;
import com.sginabreda.minesweeper.domain.exception.RequestException;
import com.sginabreda.minesweeper.domain.mapper.CellMapper;
import com.sginabreda.minesweeper.infrastructure.repository.CellRepository;
import com.sginabreda.minesweeper.infrastructure.repository.GameRepository;
import com.sginabreda.minesweeper.infrastructure.repository.model.CellModel;
import com.sginabreda.minesweeper.infrastructure.repository.model.GameModel;
import com.sginabreda.minesweeper.util.ListUtil;
import org.springframework.http.HttpStatus;

import java.util.Optional;

public class ChangeCellStatus {

	private final GameRepository gameRepository;
	private final CellRepository cellRepository;
	private final CellMapper cellMapper;

	public ChangeCellStatus(GameRepository gameRepository, CellRepository cellRepository, CellMapper cellMapper) {
		this.cellRepository = cellRepository;
		this.gameRepository = gameRepository;
		this.cellMapper = cellMapper;
	}

	public Cell invoke(Long gameId, Long cellId, CellStatusChangeRequest changeRequest) throws RequestException {
		Optional<GameModel> game = gameRepository.findById(gameId);
		Optional<CellModel> cell = cellRepository.findById(cellId);
		Status status = Status.valueOf(changeRequest.getStatus().toUpperCase());
		if (game.isEmpty()) {
			throw new RequestException("Game not found", "not.found", HttpStatus.NOT_FOUND.value());
		} else if (cell.isEmpty()) {
			throw new RequestException("Cell not found", "not.found", HttpStatus.NOT_FOUND.value());
		}
		CellModel[][] grid = ListUtil.generateGrid(game.get().getCells(), game.get().getRows());
		if (! game.get().isStarted()) {
			generateMines(game.get(), grid, cellId);
		}
		changeCellStatus(game.get(), grid, cell.get(), status);
		return cellMapper.toDomain(cell.get());
	}

	private void generateMines(GameModel gameModel, CellModel[][] grid, Long firstCell) {
		Integer minesAmount = gameModel.getMines();
		int row, column;
		int minesCreated = 0;
		while (minesCreated < minesAmount) {
			row = (int) (Math.random() * gameModel.getRows());
			column = (int) (Math.random() * gameModel.getCols());
			CellModel mine = grid[row][column];
			if (! mine.getId().equals(firstCell) && ! mine.isHasMine()) {
				mine.setHasMine(true);
				mine.setAdjacentMines(0);
				updateAdjacentMines(gameModel, grid, mine);
				minesCreated++;
			}
		}
		cellRepository.saveAll(gameModel.getCells());
	}

	private void changeCellStatus(GameModel gameModel, CellModel[][] grid, CellModel cell, Status status) {
		if (cell.getCellStatus().equals(Status.UNCLICKED.name())) {
			cell.setCellStatus(status.name());
			gameModel.increaseRevealedCells();
			if (cell.getAdjacentMines() == 0 && status == Status.REVEALED) {
				int row = cell.getCellRow();
				int column = cell.getCellCol();
				for (int row1 = Math.max(0, row - 1); row1 <= Math.min(gameModel.getRows() - 1, row + 1); row1++) {
					for (int col1 = Math.max(0, column - 1); col1 <= Math.min(gameModel.getCols() - 1,
							column + 1); col1++) {
						changeCellStatus(gameModel, grid, grid[row1][col1], Status.REVEALED);
					}
				}
			}
		}
		gameRepository.save(gameModel);
		cellRepository.saveAll(gameModel.getCells());
	}

	private void updateAdjacentMines(GameModel gameModel, CellModel[][] grid, CellModel cell) {
		int row = cell.getCellRow();
		int column = cell.getCellCol();
		for (int row1 = Math.max(0, row - 1); row1 <= Math.min(gameModel.getRows() - 1, row + 1); row1++) {
			for (int col1 = Math.max(0, column - 1); col1 <= Math.min(gameModel.getCols() - 1, column + 1); col1++) {
				if (! grid[row1][col1].isHasMine()) {
					grid[row1][col1].increaseAdjacentMines();
				}
			}
		}
	}
}
