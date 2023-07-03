package io.nuabo.hikitty.board.infrastructure;

import io.nuabo.common.domain.exception.ResourceNotFoundException;
import io.nuabo.hikitty.board.application.port.HeartRepository;
import io.nuabo.hikitty.board.domain.Heart;
import io.nuabo.hikitty.board.infrastructure.entity.HeartEntity;
import io.nuabo.hikitty.board.infrastructure.port.HeartJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class HeartRepositoryImpl implements HeartRepository {

    private final HeartJpaRepository heartJpaRepository;

    @Override
    public Heart save(Heart heart) {
        return heartJpaRepository.save(HeartEntity.from(heart)).toModel();
    }

    @Override
    public Optional<Heart> findByBoardIdAndDonerId(Long boardId, Long donerId) {
        return heartJpaRepository.findByBoardEntityIdAndDonerId(boardId, donerId).map(HeartEntity::toModel);
    }

    @Override
    public Heart getByBoardIdAndDonerId(Long boardId, Long donerId) {
        return findByBoardIdAndDonerId(boardId, donerId).orElseThrow(
                () -> new ResourceNotFoundException("heart", boardId));
    }

    @Override
    public Heart getById(Long heartId) {
        return findById(heartId).orElseThrow(
                () -> new ResourceNotFoundException("heart", heartId));
    }

    @Override
    public Optional<Heart> findById(Long heartId) {
        return heartJpaRepository.findById(heartId).map(HeartEntity::toModel);
    }

}
