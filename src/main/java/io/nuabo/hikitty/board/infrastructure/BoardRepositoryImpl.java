package io.nuabo.hikitty.board.infrastructure;

import io.nuabo.hikitty.board.application.port.BoardRepository;
import io.nuabo.hikitty.board.domain.Board;
import io.nuabo.hikitty.board.infrastructure.entity.BoardEntity;
import io.nuabo.hikitty.board.infrastructure.port.BoardJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepository {

    private final BoardJpaRepository boardJpaRepository;

    @Override
    public Board save(Board board) {
        return boardJpaRepository.save(BoardEntity.from(board)).toModel();
    }

}
