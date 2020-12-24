package com.sginabreda.minesweeper.domain.exception;

public class RevealedCellException extends Exception{

	public RevealedCellException() {
		super("Cell is already revealed!");
	}
}
