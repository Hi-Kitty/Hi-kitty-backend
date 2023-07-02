package io.nuabo.hikitty.board.infrastructure.port;

import io.nuabo.hikitty.board.infrastructure.entity.PlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanJpaRepository extends JpaRepository<PlanEntity, Long> {
}
