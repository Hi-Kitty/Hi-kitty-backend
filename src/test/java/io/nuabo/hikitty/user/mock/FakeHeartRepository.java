package io.nuabo.hikitty.user.mock;

import io.nuabo.hikitty.board.application.port.HeartRepository;
import io.nuabo.hikitty.board.domain.Heart;

import java.util.List;
import java.util.Optional;

public class FakeHeartRepository implements HeartRepository {
    @Override
    public Heart save(Heart heart) {
        return null;
    }

    @Override
    public Optional<Heart> findByBoardIdAndDonerId(Long boardId, Long donerId) {
        return Optional.empty();
    }

    @Override
    public Heart getById(Long heartId) {
        return null;
    }

    @Override
    public Optional<Heart> findById(Long heartId) {
        return Optional.empty();
    }

    @Override
    public List<Heart> findAllByBoardId(Long boardId) {
        return null;
    }

    @Override
    public List<Heart> getAllByUserId(Long id) {
        return null;
    }

    @Override
    public void saveAll(List<Heart> hearts) {

    }
}
