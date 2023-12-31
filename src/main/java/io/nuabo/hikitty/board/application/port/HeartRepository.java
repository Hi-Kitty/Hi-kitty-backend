package io.nuabo.hikitty.board.application.port;

import io.nuabo.hikitty.board.domain.Heart;
import io.nuabo.hikitty.board.domain.Status;

import java.util.List;
import java.util.Optional;

public interface HeartRepository {
    Heart save(Heart heart);

    Optional<Heart> findByBoardIdAndDonerId(Long boardId, Long donerId);

    Heart getById(Long heartId);

    Optional<Heart> findById(Long heartId);

    List<Heart> findAllByBoardId(Long boardId);

    List<Heart> getAllByUserId(Long id);

    void saveAll(List<Heart> hearts);

    List<Heart> findAllByBoardIdAndStatus(Long boardId, Status status);
}
