package com.sginabreda.minesweeper.application.config;

import com.sginabreda.minesweeper.domain.mapper.CellMapper;
import com.sginabreda.minesweeper.domain.mapper.GameMapper;
import com.sginabreda.minesweeper.domain.usecase.ChangeCellStatus;
import com.sginabreda.minesweeper.domain.usecase.CreateGame;
import com.sginabreda.minesweeper.domain.usecase.GetGame;
import com.sginabreda.minesweeper.domain.usecase.ListCells;
import com.sginabreda.minesweeper.domain.usecase.ListGames;
import com.sginabreda.minesweeper.infrastructure.repository.CellRepository;
import com.sginabreda.minesweeper.infrastructure.repository.GameRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GameConfiguration {

	@Bean
	public CreateGame createGame(GameRepository gameRepository, CellRepository cellRepository, GameMapper gameMapper, CellMapper cellMapper) {
		return new CreateGame(gameRepository, cellRepository, gameMapper, cellMapper);
	}

	@Bean
	public ListGames listGames(GameRepository gameRepository, GameMapper gameMapper) {
		return new ListGames(gameRepository, gameMapper);
	}

	@Bean
	public GetGame getGame(GameRepository gameRepository, GameMapper gameMapper) {
		return new GetGame(gameRepository, gameMapper);
	}

	@Bean
	public ChangeCellStatus changeCellStatus(GameRepository gameRepository, CellRepository cellRepository, CellMapper cellMapper) {
		return new ChangeCellStatus(gameRepository, cellRepository, cellMapper);
	}

	@Bean
	public ListCells listCells(GameRepository gameRepository, CellMapper cellMapper) {
		return new ListCells(gameRepository, cellMapper);
	}

	@Bean
	public CellMapper cellMapper() {
		return new CellMapper();
	}

	@Bean
	public GameMapper gameMapper(CellMapper cellMapper) {
		return new GameMapper(cellMapper);
	}
}
