package com.sginabreda.minesweeper.infrastructure.repository.model;

import com.sginabreda.minesweeper.domain.entity.Cell;
import com.sginabreda.minesweeper.domain.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@Getter
@Setter
public class CellModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Integer cellRow;
	private Integer cellCol;
	private String cellStatus;
	private Integer adjacentMines;
	private boolean hasMine;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "game_id",
	            nullable = false)
	private GameModel game;

	public CellModel(Integer cellRow, Integer cellCol, String cellStatus, Integer adjacentMines, GameModel gameModel) {
		this.cellRow = cellRow;
		this.cellCol = cellCol;
		this.cellStatus = cellStatus;
		this.adjacentMines = adjacentMines;
		this.hasMine = false;
		this.game = gameModel;
	}
}
