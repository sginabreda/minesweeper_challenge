package com.sginabreda.minesweeper.config;

import com.sginabreda.minesweeper.domain.usecase.ChangeCellStatus;
import com.sginabreda.minesweeper.domain.usecase.CreateGame;
import com.sginabreda.minesweeper.domain.usecase.GetGame;
import com.sginabreda.minesweeper.domain.usecase.ListGames;
import com.sginabreda.minesweeper.infrastructure.repository.CellRepository;
import com.sginabreda.minesweeper.infrastructure.repository.GameRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GameConfiguration {

	@Bean
	public CreateGame createGame(GameRepository gameRepository, CellRepository cellRepository) {
		return new CreateGame(gameRepository, cellRepository);
	}

	@Bean
	public ListGames listGames() {
		return new ListGames();
	}

	@Bean
	public GetGame getGame(GameRepository gameRepository) {
		return new GetGame(gameRepository);
	}

	@Bean
	public ChangeCellStatus changeCellStatus() {
		return new ChangeCellStatus();
	}
}
