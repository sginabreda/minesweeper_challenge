package com.sginabreda.minesweeper.delivery.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CellStatusChangeRequest {

	@Pattern(regexp = "(revealed|red_flag|question_mark)", flags = Pattern.Flag.CASE_INSENSITIVE)
	private String status;
}
