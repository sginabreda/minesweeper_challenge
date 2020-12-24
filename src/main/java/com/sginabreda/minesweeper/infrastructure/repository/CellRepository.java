package com.sginabreda.minesweeper.infrastructure.repository;

import com.sginabreda.minesweeper.infrastructure.repository.model.CellModel;
import com.sginabreda.minesweeper.infrastructure.repository.model.GameModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CellRepository extends JpaRepository<CellModel, Long> {

	List<CellModel> findAllByGame(GameModel gameModel);
}
