package com.sginabreda.minesweeper.infrastructure.repository;

import com.sginabreda.minesweeper.infrastructure.repository.model.GameModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<GameModel, Long> {
}
