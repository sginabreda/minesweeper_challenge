package com.sginabreda.minesweeper.config;

import com.sginabreda.minesweeper.domain.usecase.ChangeCellStatus;
import com.sginabreda.minesweeper.domain.usecase.CreateGame;
import com.sginabreda.minesweeper.domain.usecase.GetGame;
import com.sginabreda.minesweeper.domain.usecase.ListGames;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GameConfiguration {

	@Bean
	public CreateGame createGame() {
		return new CreateGame();
	}

	@Bean
	public ListGames listGames() {
		return new ListGames();
	}

	@Bean
	public GetGame getGame() {
		return new GetGame();
	}

	@Bean
	public ChangeCellStatus changeCellStatus() {
		return new ChangeCellStatus();
	}
}
