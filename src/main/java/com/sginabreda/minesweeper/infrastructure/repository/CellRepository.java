package com.sginabreda.minesweeper.infrastructure.repository;

import com.sginabreda.minesweeper.infrastructure.repository.model.CellModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CellRepository extends JpaRepository<CellModel, Long> {
}
