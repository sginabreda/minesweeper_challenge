package com.sginabreda.minesweeper.infrastructure.repository.model;

import com.sginabreda.minesweeper.domain.entity.Game;
import lombok.AllArgsConstructor;
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

import static java.util.stream.Collectors.toList;

@Entity
@Table(name = "games",
       schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class GameModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "game_id")
	private Long id;
	private Integer rows;
	private Integer cols;
	private Integer mines;
	@OneToMany(fetch = FetchType.LAZY,
	           mappedBy = "game")
	private List<CellModel> cells;

	public Game toDomain() {
		return new Game(id, rows, cols, mines, cells.stream().map(CellModel::toDomain).collect(toList()));
	}

	public GameModel(Long id, Integer rows, Integer cols, Integer mines) {
		this.id = id;
		this.rows = rows;
		this.cols = cols;
		this.mines = mines;
	}
}
