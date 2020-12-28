package com.sginabreda.minesweeper.domain.mapper;

import com.sginabreda.minesweeper.delivery.dto.request.GameRequest;
import com.sginabreda.minesweeper.delivery.dto.response.GameDto;
import com.sginabreda.minesweeper.domain.entity.Game;
import com.sginabreda.minesweeper.infrastructure.repository.model.GameModel;

import static java.util.stream.Collectors.toList;

public class GameMapper {

	private final CellMapper cellMapper;

	public GameMapper(CellMapper cellMapper) {
		this.cellMapper = cellMapper;
	}

	public Game toDomain(GameRequest gameRequest) {
		Game game = new Game();
		game.setCols(gameRequest.getCols());
		game.setRows(gameRequest.getRows());
		game.setMines(gameRequest.getMines());

		return game;
	}

	public Game toDomain(GameModel gameModel) {
		Game game = new Game();
		game.setId(gameModel.getId());
		game.setCols(gameModel.getCols());
		game.setRows(gameModel.getRows());
		game.setMines(gameModel.getMines());
		game.setRevealedMines(gameModel.getRevealedMines());
		game.setCells(gameModel.getCells().stream().map(cellMapper::toDomain).collect(toList()));

		return game;
	}

	public GameModel toModel(Game game) {
		GameModel gameModel = new GameModel();
		gameModel.setId(game.getId());
		gameModel.setCols(game.getCols());
		gameModel.setRows(game.getRows());
		gameModel.setMines(game.getMines());
		gameModel.setCells(game.getCells().stream().map(cell -> cellMapper.toModel(cell, gameModel)).collect(toList()));

		return gameModel;
	}

	public GameDto toDto(Game game) {
		GameDto gameDto = new GameDto();
		gameDto.setId(game.getId());
		gameDto.setCols(game.getCols());
		gameDto.setRows(game.getRows());
		gameDto.setRevealedMines(game.getRevealedMines());
		gameDto.setMines(game.getMines());

		return gameDto;
	}
}
