package com.sginabreda.minesweeper.domain.usecase;

import com.sginabreda.minesweeper.domain.entity.Game;
import com.sginabreda.minesweeper.domain.exception.GameNotFoundException;
import com.sginabreda.minesweeper.domain.mapper.GameMapper;
import com.sginabreda.minesweeper.infrastructure.repository.GameRepository;
import com.sginabreda.minesweeper.infrastructure.repository.model.GameModel;

import java.util.Optional;

public class GetGame {

	private final GameRepository gameRepository;
	private final GameMapper gameMapper;

	public Game invoke(Long gameId) throws GameNotFoundException {
		Optional<GameModel> gameModelOptional = gameRepository.findById(gameId);
		if (gameModelOptional.isEmpty()) {
			throw new GameNotFoundException();
		}
		return gameMapper.toDomain(gameModelOptional.get());
	}

	public GetGame(GameRepository gameRepository, GameMapper gameMapper) {
		this.gameRepository = gameRepository;
		this.gameMapper = gameMapper;
	}
}
