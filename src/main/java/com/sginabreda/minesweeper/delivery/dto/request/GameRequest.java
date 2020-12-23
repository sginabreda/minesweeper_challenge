package com.sginabreda.minesweeper.delivery.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GameRequest {

	@Min(5)
	@Max(10)
	private Integer rows;
	@Min(5)
	@Max(10)
	private Integer cols;
	@Min(1)
	private Integer mines;
}
