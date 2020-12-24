package com.sginabreda.minesweeper.delivery;

import com.sginabreda.minesweeper.delivery.dto.request.CellStatusChangeRequest;
import com.sginabreda.minesweeper.delivery.dto.request.GameRequest;
import com.sginabreda.minesweeper.delivery.dto.response.CellDto;
import com.sginabreda.minesweeper.delivery.dto.response.GameDto;
import com.sginabreda.minesweeper.domain.exception.BadRequestException;
import com.sginabreda.minesweeper.domain.exception.GameNotFoundException;

import javax.validation.Valid;
import java.util.List;

public interface GameController {

	GameDto createGame(@Valid GameRequest gameRequest) throws BadRequestException;

	List<GameDto> listGames();

	GameDto getGame(Long id);

	List<CellDto> listCells(Long gameId) throws GameNotFoundException;

	CellDto changeCellStatus(Long gameId, Long cellId, @Valid CellStatusChangeRequest status);
}
