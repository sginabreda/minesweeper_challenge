package com.sginabreda.minesweeper.domain.usecase;

import com.sginabreda.minesweeper.delivery.dto.request.GameRequest;
import com.sginabreda.minesweeper.delivery.dto.response.GameDto;

public class CreateGame {

	public GameDto execute(GameRequest game) {
		// TODO Add validations
		return new GameDto(game.getRows(), game.getCols(), game.getMines());
	}
}
