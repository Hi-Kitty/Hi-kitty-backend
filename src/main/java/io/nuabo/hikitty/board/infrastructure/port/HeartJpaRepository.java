package io.nuabo.hikitty.board.infrastructure.port;

import io.nuabo.hikitty.board.infrastructure.entity.HeartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeartJpaRepository extends JpaRepository<HeartEntity, Long> {
}
