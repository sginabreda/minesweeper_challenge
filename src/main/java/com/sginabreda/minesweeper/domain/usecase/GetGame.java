package com.sginabreda.minesweeper.domain.usecase;

import com.sginabreda.minesweeper.domain.entity.Game;
import com.sginabreda.minesweeper.infrastructure.repository.GameRepository;

public class GetGame {

	private GameRepository repository;

	public Game execute(Long gameId) {
		return repository.getOne(gameId).toDomain();
	}

	public GetGame(GameRepository gameRepository) {
		this.repository = gameRepository;
	}
}
