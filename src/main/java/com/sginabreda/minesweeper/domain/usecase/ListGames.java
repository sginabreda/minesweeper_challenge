package com.sginabreda.minesweeper.domain.usecase;

import com.sginabreda.minesweeper.domain.entity.Game;
import com.sginabreda.minesweeper.infrastructure.repository.GameRepository;
import com.sginabreda.minesweeper.infrastructure.repository.model.GameModel;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class ListGames {

	private final GameRepository gameRepository;

	public List<Game> invoke() {
		return gameRepository.findAll().stream().map(GameModel::toDomain).collect(toList());
	}

	public ListGames(GameRepository gameRepository) {
		this.gameRepository = gameRepository;
	}
}
