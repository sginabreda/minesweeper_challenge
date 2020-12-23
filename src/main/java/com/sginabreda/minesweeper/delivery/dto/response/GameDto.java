package com.sginabreda.minesweeper.delivery.dto.response;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class GameDto {

	private Integer rows;
	private Integer cols;
	private Integer mines;
	private List<CellDto> cells;
}