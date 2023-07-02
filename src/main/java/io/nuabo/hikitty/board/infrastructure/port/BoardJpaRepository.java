package io.nuabo.hikitty.board.infrastructure.port;

import io.nuabo.hikitty.board.infrastructure.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardJpaRepository extends JpaRepository<BoardEntity, Long> {
}
