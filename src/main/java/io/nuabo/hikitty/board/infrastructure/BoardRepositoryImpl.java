package io.nuabo.hikitty.board.infrastructure;

import io.nuabo.common.domain.exception.ResourceNotFoundException;
import io.nuabo.hikitty.board.application.port.BoardRepository;
import io.nuabo.hikitty.board.domain.Board;
import io.nuabo.hikitty.board.infrastructure.entity.BoardEntity;
import io.nuabo.hikitty.board.infrastructure.port.BoardJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepository {

    private final BoardJpaRepository boardJpaRepository;

    @Override
    public Board save(Board board) {
        return boardJpaRepository.save(BoardEntity.from(board)).toModel();
    }

    @Override
    public Board getById(Long id) {
        return findById(id).orElseThrow(
                () -> new ResourceNotFoundException("board", id)
        );
    }

    @Override
    public Optional<Board> findById(Long id) {
        return boardJpaRepository.findById(id).map(BoardEntity::toModel);
    }

}
