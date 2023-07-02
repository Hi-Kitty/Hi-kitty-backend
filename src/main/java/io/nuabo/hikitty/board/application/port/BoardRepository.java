package io.nuabo.hikitty.board.application.port;

import io.nuabo.hikitty.board.domain.Board;

public interface BoardRepository {
    Board save(Board board);
}
