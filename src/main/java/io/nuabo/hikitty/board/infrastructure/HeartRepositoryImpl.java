package io.nuabo.hikitty.board.infrastructure;

import io.nuabo.common.domain.exception.ResourceNotFoundException;
import io.nuabo.hikitty.board.application.port.HeartRepository;
import io.nuabo.hikitty.board.domain.Heart;
import io.nuabo.hikitty.board.infrastructure.entity.HeartEntity;
import io.nuabo.hikitty.board.infrastructure.port.HeartJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
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
    public Heart getById(Long heartId) {
        return findById(heartId).orElseThrow(
                () -> new ResourceNotFoundException("heart", heartId));
    }

    @Override
    public Optional<Heart> findById(Long heartId) {
        return heartJpaRepository.findById(heartId).map(HeartEntity::toModel);
    }

    @Override
    public List<Heart> findAllByBoardId(Long boardId) {
        return heartJpaRepository.findAllByBoardEntityId(boardId).stream().map(HeartEntity::toModel).toList();
    }

    @Override
    public List<Heart> getAllByUserId(Long id) {
        return heartJpaRepository.findAllByDonerId(id).stream().map(HeartEntity::toModel).toList();
    }

    @Override
    public void saveAll(List<Heart> hearts) {
        heartJpaRepository.saveAll(hearts.stream().map(HeartEntity::from).toList());
    }

}
