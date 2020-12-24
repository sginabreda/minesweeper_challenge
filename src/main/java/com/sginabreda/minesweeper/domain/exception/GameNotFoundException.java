package com.sginabreda.minesweeper.domain.exception;

public class GameNotFoundException extends Exception {

	public GameNotFoundException() {
		super("Game does not exist!");
	}
}
