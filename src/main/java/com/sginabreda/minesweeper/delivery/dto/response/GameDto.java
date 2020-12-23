package com.sginabreda.minesweeper.delivery.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GameDto {

	private Long id;
	private Integer rows;
	private Integer cols;
	private Integer mines;
	private List<CellDto> cells;
}
