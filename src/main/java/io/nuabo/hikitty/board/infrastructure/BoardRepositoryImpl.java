package io.nuabo.hikitty.board.infrastructure;

import io.nuabo.common.domain.exception.ResourceNotFoundException;
import io.nuabo.hikitty.board.application.port.BoardRepository;
import io.nuabo.hikitty.board.domain.Board;
import io.nuabo.hikitty.board.infrastructure.entity.BoardEntity;
import io.nuabo.hikitty.board.infrastructure.port.BoardJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    @Override
    public List<Board> getAllByUserId(Long id) {
        return boardJpaRepository.findAllByFundraiserId(id).stream().map(BoardEntity::toModel).toList();
    }

    @Override
    public void saveAll(List<Board> boards) {
        boardJpaRepository.saveAll(boards.stream().map(BoardEntity::from).toList());
    }
}
