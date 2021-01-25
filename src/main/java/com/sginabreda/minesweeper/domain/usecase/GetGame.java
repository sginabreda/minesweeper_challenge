package com.sginabreda.minesweeper.domain.usecase;

import com.sginabreda.minesweeper.domain.entity.Game;
import com.sginabreda.minesweeper.domain.exception.RequestException;
import com.sginabreda.minesweeper.domain.mapper.GameMapper;
import com.sginabreda.minesweeper.infrastructure.repository.GameRepository;
import com.sginabreda.minesweeper.infrastructure.repository.model.GameModel;
import org.springframework.http.HttpStatus;

public class GetGame {

	private final GameRepository gameRepository;
	private final GameMapper gameMapper;

	public Game invoke(Long gameId) throws RequestException {
		GameModel game = gameRepository.findById(gameId).orElseThrow(
				() -> new RequestException("Game not found", "not.found", HttpStatus.NOT_FOUND.value()));

		return gameMapper.toDomain(game);
	}

	public GetGame(GameRepository gameRepository, GameMapper gameMapper) {
		this.gameRepository = gameRepository;
		this.gameMapper = gameMapper;
	}
}
