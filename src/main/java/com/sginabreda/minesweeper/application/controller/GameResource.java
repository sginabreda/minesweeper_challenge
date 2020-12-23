package com.sginabreda.minesweeper.application.controller;

import com.sginabreda.minesweeper.delivery.GameController;
import com.sginabreda.minesweeper.delivery.dto.request.CellStatusChangeRequest;
import com.sginabreda.minesweeper.delivery.dto.request.GameRequest;
import com.sginabreda.minesweeper.delivery.dto.response.CellDto;
import com.sginabreda.minesweeper.delivery.dto.response.GameDto;
import com.sginabreda.minesweeper.domain.entity.Game;
import com.sginabreda.minesweeper.domain.exception.BadRequestException;
import com.sginabreda.minesweeper.domain.usecase.ChangeCellStatus;
import com.sginabreda.minesweeper.domain.usecase.CreateGame;
import com.sginabreda.minesweeper.domain.usecase.GetGame;
import com.sginabreda.minesweeper.domain.usecase.ListGames;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Validated
@RestController
@RequestMapping(value = "/games",
                produces = {"application/json"})
public class GameResource implements GameController {

	private final CreateGame createGame;
	private final ListGames listGames;
	private final GetGame getGame;
	private final ChangeCellStatus changeCellStatus;

	@Override
	@PostMapping(consumes = {"application/json"})
	public GameDto createGame(@RequestBody GameRequest newGame) throws BadRequestException {
		return createGame.execute(newGame).toDto();
	}

	@Override
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<GameDto> listGames() {
		return listGames.execute().stream().map(Game::toDto).collect(toList());
	}

	@Override
	@GetMapping(value = "/{gameId}",
	            produces = {"application/json"})
	@ResponseStatus(HttpStatus.OK)
	public GameDto getGame(@PathVariable Long gameId) {
		return getGame.execute(gameId).toDto();
	}

	@Override
	@GetMapping("/{gameId}/cells/{cellId}")
	@ResponseStatus(HttpStatus.OK)
	public CellDto changeCellStatus(
			@PathVariable Long gameId, @PathVariable Long cellId, @RequestBody CellStatusChangeRequest status) {
		return changeCellStatus.execute(gameId, cellId, status);
	}

	public GameResource(CreateGame createGame, ListGames listGames, GetGame getGame, ChangeCellStatus changeCellStatus) {
		this.createGame = createGame;
		this.listGames = listGames;
		this.getGame = getGame;
		this.changeCellStatus = changeCellStatus;
	}
}
