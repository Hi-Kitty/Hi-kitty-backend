package io.nuabo.hikitty.board.infrastructure.port;

import io.nuabo.hikitty.board.infrastructure.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageJpaRepository extends JpaRepository<ImageEntity, Long> {
}
