package com.sginabreda.minesweeper.application.exceptionhandler;

import com.sginabreda.minesweeper.delivery.dto.ApiError;
import com.sginabreda.minesweeper.domain.exception.BadRequestException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class MinesweeperExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = BadRequestException.class)
	public ResponseEntity handleBadRequestException(BadRequestException ex, WebRequest request) {
		ApiError error = new ApiError("bad.request", ex.getMessage());
		return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ApiError error = new ApiError("internal.error", ex.getMessage());
		return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
