package com.sginabreda.minesweeper.config.context;

public enum Header {
	AUTHORIZATION("Authorization");

	public final String label;

	Header(String label) {
		this.label = label;
	}
}
