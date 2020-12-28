package com.sginabreda.minesweeper.domain.usecase;

import com.sginabreda.minesweeper.domain.entity.Game;
import com.sginabreda.minesweeper.domain.mapper.GameMapper;
import com.sginabreda.minesweeper.infrastructure.repository.GameRepository;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class ListGames {

	private final GameRepository gameRepository;
	private final GameMapper gameMapper;

	public List<Game> invoke() {
		return gameRepository.findAll().stream().map(gameMapper::toDomain).collect(toList());
	}

	public ListGames(GameRepository gameRepository, GameMapper gameMapper) {
		this.gameRepository = gameRepository;
		this.gameMapper = gameMapper;
	}
}
