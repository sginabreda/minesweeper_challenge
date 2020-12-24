package com.sginabreda.minesweeper.domain.exception;

public class CellNotFoundException extends Exception {

	public CellNotFoundException() {
		super("Cell does not exist!");
	}
}
