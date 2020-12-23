package com.sginabreda.minesweeper.infrastructure.repository.model;

import com.sginabreda.minesweeper.domain.entity.Cell;
import com.sginabreda.minesweeper.domain.enums.Status;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cells",
       schema = "public")
@AllArgsConstructor
@NoArgsConstructor
public class CellModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Integer cellRow;
	private Integer cellCol;
	private String cellStatus;
	private Integer adjacentMines;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "game_id",
	            nullable = false)
	private GameModel game;

	public Cell toDomain() {
		return new Cell(id, cellRow, cellCol, Status.valueOf(cellStatus), adjacentMines);
	}

	public CellModel(Integer cellRow, Integer cellCol, String cellStatus, Integer adjacentMines, GameModel gameModel) {
		this.cellRow = cellRow;
		this.cellCol = cellCol;
		this.cellStatus = cellStatus;
		this.adjacentMines = adjacentMines;
		this.game = gameModel;
	}
}
