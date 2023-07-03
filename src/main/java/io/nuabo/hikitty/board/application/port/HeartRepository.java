package io.nuabo.hikitty.board.application.port;

import io.nuabo.hikitty.board.domain.Heart;

import java.util.List;
import java.util.Optional;

public interface HeartRepository {
    Heart save(Heart heart);

    Optional<Heart> findByBoardIdAndDonerId(Long boardId, Long donerId);

    Heart getById(Long heartId);

    Optional<Heart> findById(Long heartId);

    List<Heart> findAllByBoardId(Long boardId);
}
