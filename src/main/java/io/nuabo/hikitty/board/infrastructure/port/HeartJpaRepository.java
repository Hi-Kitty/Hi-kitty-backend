package io.nuabo.hikitty.board.infrastructure.port;

import io.nuabo.hikitty.board.infrastructure.entity.HeartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HeartJpaRepository extends JpaRepository<HeartEntity, Long> {

    Optional<HeartEntity> findByBoardEntityIdAndDonerId(Long boardId, Long donerId);

    List<HeartEntity> findAllByBoardEntityId(Long boardId);
}
