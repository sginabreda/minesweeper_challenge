package com.sginabreda.minesweeper.delivery;

import com.sginabreda.minesweeper.delivery.dto.request.CellStatusChangeRequest;
import com.sginabreda.minesweeper.delivery.dto.request.GameRequest;
import com.sginabreda.minesweeper.delivery.dto.response.CellDto;
import com.sginabreda.minesweeper.delivery.dto.response.GameDto;
import com.sginabreda.minesweeper.domain.exception.RequestException;

import javax.validation.Valid;
import java.util.List;

public interface GameController {

	GameDto createGame(@Valid GameRequest gameRequest) throws RequestException;

	List<GameDto> listGames();

	GameDto getGame(Long id) throws RequestException;

	List<CellDto> listCells(Long gameId) throws RequestException;

	CellDto changeCellStatus(Long gameId, Long cellId, @Valid CellStatusChangeRequest status) throws RequestException;
}
