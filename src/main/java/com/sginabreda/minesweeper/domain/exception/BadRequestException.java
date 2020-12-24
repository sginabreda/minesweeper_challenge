package com.sginabreda.minesweeper.domain.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BadRequestException extends IllegalArgumentException {

	private final String message;
}
