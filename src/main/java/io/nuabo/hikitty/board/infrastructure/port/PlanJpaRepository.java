package io.nuabo.hikitty.board.infrastructure.port;

import io.nuabo.hikitty.board.infrastructure.entity.PlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanJpaRepository extends JpaRepository<PlanEntity, Long> {
    List<PlanEntity> findAllByBoardEntityId(Long boardId);
}
