package com.sginabreda.minesweeper.infrastructure.repository.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "games",
       schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class GameModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "game_id")
	private Long id;
	private Integer rows;
	private Integer cols;
	private Integer mines;
	private Integer revealedMines;
	@OneToMany(fetch = FetchType.LAZY,
	           mappedBy = "game")
	private List<CellModel> cells;

	public GameModel(Long id, Integer rows, Integer cols, Integer mines) {
		this.id = id;
		this.rows = rows;
		this.cols = cols;
		this.mines = mines;
		this.revealedMines = 0;
	}

	public boolean isStarted() {
		return this.revealedMines != 0;
	}
}
