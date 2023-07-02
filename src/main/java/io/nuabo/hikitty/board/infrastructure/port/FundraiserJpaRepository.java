package io.nuabo.hikitty.board.infrastructure.port;

import io.nuabo.hikitty.board.infrastructure.entity.FundraiserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FundraiserJpaRepository extends JpaRepository<FundraiserEntity, Long> {
}
