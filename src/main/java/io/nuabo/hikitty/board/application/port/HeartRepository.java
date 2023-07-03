package io.nuabo.hikitty.board.application.port;

import io.nuabo.hikitty.board.domain.Heart;

import java.util.Optional;

public interface HeartRepository {
    Heart save(Heart heart);

    Optional<Heart> findByBoardIdAndDonerId(Long boardId, Long donerId);

    Heart getByBoardIdAndDonerId(Long boardId, Long donerId);

    Heart getById(Long heartId);

    Optional<Heart> findById(Long heartId);
}
