package com.sginabreda.minesweeper.domain.usecase;

import com.sginabreda.minesweeper.delivery.dto.response.GameDto;

import java.util.List;

public class ListGames {

	public List<GameDto> execute() {
		return List.of(new GameDto(10, 10, 10));
	}
}
