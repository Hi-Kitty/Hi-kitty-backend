package io.nuabo.hikitty.toss.infrastructure.port;

import io.nuabo.hikitty.toss.infrastructure.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardJpaRepository extends JpaRepository<CardEntity, Long> {
}
