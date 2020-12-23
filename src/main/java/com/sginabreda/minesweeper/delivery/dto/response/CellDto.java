package com.sginabreda.minesweeper.delivery.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CellDto {

	private Long id;
	private Integer row;
	private Integer col;
	private String status;
	private Integer adjacentMines;
}
