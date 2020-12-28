package com.sginabreda.minesweeper.domain.exception;

import lombok.Getter;

@Getter
public class RequestException extends Exception {

	private String message;
	private String code;
	private Integer status;

	public RequestException(String message, String code, Integer status) {
		super(message);
		this.code = code;
		this.status = status;
	}
}
