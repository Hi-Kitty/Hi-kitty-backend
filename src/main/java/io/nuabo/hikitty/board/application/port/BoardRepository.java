package io.nuabo.hikitty.board.application.port;

import io.nuabo.hikitty.board.domain.Board;

import java.util.Optional;

public interface BoardRepository {
    Board save(Board board);
    Board getById(Long id);
    Optional<Board> findById(Long id);
}
