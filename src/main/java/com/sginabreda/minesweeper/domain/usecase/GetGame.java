package com.sginabreda.minesweeper.domain.usecase;

import com.sginabreda.minesweeper.domain.entity.Game;
import com.sginabreda.minesweeper.domain.exception.GameNotFoundException;
import com.sginabreda.minesweeper.infrastructure.repository.GameRepository;
import com.sginabreda.minesweeper.infrastructure.repository.model.GameModel;

import java.util.Optional;

public class GetGame {

	private final GameRepository gameRepository;

	public Game invoke(Long gameId) throws GameNotFoundException {
		Optional<GameModel> gameModelOptional = gameRepository.findById(gameId);
		if (gameModelOptional.isEmpty()) {
			throw new GameNotFoundException();
		}
		return gameModelOptional.get().toDomain();
	}

	public GetGame(GameRepository gameRepository) {
		this.gameRepository = gameRepository;
	}
}
