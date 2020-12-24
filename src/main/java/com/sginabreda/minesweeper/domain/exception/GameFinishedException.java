package com.sginabreda.minesweeper.domain.exception;

public class GameFinishedException extends Exception {

	public GameFinishedException() {
		super("Game status is finished!");
	}
}
