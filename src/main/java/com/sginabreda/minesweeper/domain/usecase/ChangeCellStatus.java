package com.sginabreda.minesweeper.domain.usecase;

import com.sginabreda.minesweeper.delivery.dto.request.CellStatusChangeRequest;
import com.sginabreda.minesweeper.delivery.dto.response.CellDto;

import static com.sginabreda.minesweeper.domain.enums.Status.REVEALED;

public class ChangeCellStatus {

	public CellDto execute(Long gameId, Long cellId, CellStatusChangeRequest newStatus) {
		return new CellDto(1, 1, REVEALED.name(), null);
	}
}
