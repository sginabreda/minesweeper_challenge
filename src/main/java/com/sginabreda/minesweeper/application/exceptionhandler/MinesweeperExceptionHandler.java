package com.sginabreda.minesweeper.application.exceptionhandler;

import com.sginabreda.minesweeper.delivery.dto.ApiError;
import com.sginabreda.minesweeper.domain.exception.BadRequestException;
import com.sginabreda.minesweeper.domain.exception.CellNotFoundException;
import com.sginabreda.minesweeper.domain.exception.GameNotFoundException;
import com.sginabreda.minesweeper.domain.exception.RequestException;
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
	public ResponseEntity<ApiError> handleBadRequestException(BadRequestException ex, WebRequest request) {
		ApiError error = new ApiError("bad.request", ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = {GameNotFoundException.class, CellNotFoundException.class})
	public ResponseEntity<ApiError> handleDomainException(Exception ex, WebRequest webRequest) {
		ApiError error = new ApiError("not.found", ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	@Override
	protected ResponseEntity handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ApiError error = new ApiError("internal.error", ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = {RequestException.class})
	public ResponseEntity<ApiError> handleRequestException(RequestException ex, WebRequest webRequest) {
		ApiError apiError = new ApiError(ex.getCode(), ex.getMessage());
		return new ResponseEntity<>(apiError, HttpStatus.valueOf(ex.getStatus()));
	}
}
