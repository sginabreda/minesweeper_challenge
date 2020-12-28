package com.sginabreda.minesweeper.infrastructure.repository;

import com.sginabreda.minesweeper.infrastructure.repository.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
}
