package io.nuabo.hikitty.user.mock;

import io.nuabo.hikitty.board.application.port.BoardRepository;
import io.nuabo.hikitty.board.domain.Board;

import java.util.List;
import java.util.Optional;

public class FakeBoardRepository implements BoardRepository {

    @Override
    public Board save(Board board) {
        return null;
    }

    @Override
    public Board getById(Long id) {
        return null;
    }

    @Override
    public Optional<Board> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Board> getAllByUserId(Long id) {
        return null;
    }

    @Override
    public void saveAll(List<Board> boards) {

    }
}
