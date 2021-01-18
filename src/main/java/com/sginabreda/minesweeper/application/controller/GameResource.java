package com.sginabreda.minesweeper.application.controller;

import com.sginabreda.minesweeper.delivery.GameController;
import com.sginabreda.minesweeper.delivery.dto.request.CellStatusChangeRequest;
import com.sginabreda.minesweeper.delivery.dto.request.GameRequest;
import com.sginabreda.minesweeper.delivery.dto.response.CellDto;
import com.sginabreda.minesweeper.delivery.dto.response.GameDto;
import com.sginabreda.minesweeper.domain.exception.RequestException;
import com.sginabreda.minesweeper.domain.mapper.CellMapper;
import com.sginabreda.minesweeper.domain.mapper.GameMapper;
import com.sginabreda.minesweeper.domain.usecase.ChangeCellStatus;
import com.sginabreda.minesweeper.domain.usecase.CreateGame;
import com.sginabreda.minesweeper.domain.usecase.GetGame;
import com.sginabreda.minesweeper.domain.usecase.ListCells;
import com.sginabreda.minesweeper.domain.usecase.ListGames;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Validated
@RestController
@RequestMapping(value = "/games",
                produces = {"application/json"})
@PreAuthorize("hasRole('PLAYER')")
public class GameResource implements GameController {

	private final CreateGame createGame;
	private final ListGames listGames;
	private final GetGame getGame;
	private final ListCells listCells;
	private final ChangeCellStatus changeCellStatus;
	private final GameMapper gameMapper;
	private final CellMapper cellMapper;

	@Override
	@PostMapping(consumes = {"application/json"})
	@ResponseStatus(HttpStatus.CREATED)
	public GameDto createGame(@Valid @RequestBody GameRequest newGame) throws RequestException {
		return gameMapper.toDto(createGame.invoke(newGame));
	}

	@Override
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<GameDto> listGames() {
		return listGames.invoke().stream().map(gameMapper::toDto).collect(toList());
	}

	@Override
	@GetMapping(value = "/{gameId}",
	            produces = {"application/json"})
	@ResponseStatus(HttpStatus.OK)
	public GameDto getGame(@PathVariable Long gameId) throws RequestException {
		return gameMapper.toDto(getGame.invoke(gameId));
	}

	@Override
	@GetMapping(value = "/{gameId}/cells",
	            produces = {"application/json"})
	@ResponseStatus(HttpStatus.OK)
	public List<CellDto> listCells(@PathVariable Long gameId) throws RequestException {
		return listCells.invoke(gameId).stream().map(cellMapper::toDto).collect(toList());
	}

	@Override
	@PutMapping("/{gameId}/cells/{cellId}")
	@ResponseStatus(HttpStatus.OK)
	public CellDto changeCellStatus(
			@PathVariable Long gameId,
			@PathVariable Long cellId, @RequestBody
			CellStatusChangeRequest status) throws RequestException {
		return cellMapper.toDto(changeCellStatus.invoke(gameId, cellId, status));
	}

	public GameResource(CreateGame createGame, ListGames listGames, GetGame getGame, ListCells listCells, ChangeCellStatus changeCellStatus, GameMapper gameMapper, CellMapper cellMapper) {
		this.createGame = createGame;
		this.listGames = listGames;
		this.getGame = getGame;
		this.listCells = listCells;
		this.changeCellStatus = changeCellStatus;
		this.gameMapper = gameMapper;
		this.cellMapper = cellMapper;
	}
}
