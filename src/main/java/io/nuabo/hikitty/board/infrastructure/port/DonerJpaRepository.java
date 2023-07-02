package io.nuabo.hikitty.board.infrastructure.port;

import io.nuabo.hikitty.board.infrastructure.entity.DonerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonerJpaRepository extends JpaRepository<DonerEntity, Long> {
}
