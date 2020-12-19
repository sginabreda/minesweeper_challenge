package com.sginabreda.minesweeper.domain.usecase;

import com.sginabreda.minesweeper.delivery.dto.response.GameDto;

public class GetGame {

	public GameDto execute(Long gameId) {
		return new GameDto(10, 10, 10);
	}
}
