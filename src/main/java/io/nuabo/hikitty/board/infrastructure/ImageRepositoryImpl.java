package io.nuabo.hikitty.board.infrastructure;

import io.nuabo.common.domain.exception.ResourceNotFoundException;
import io.nuabo.hikitty.board.application.port.ImageRepository;
import io.nuabo.hikitty.board.domain.Image;
import io.nuabo.hikitty.board.infrastructure.entity.ImageEntity;
import io.nuabo.hikitty.board.infrastructure.port.ImageJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ImageRepositoryImpl implements ImageRepository {

    private final ImageJpaRepository imageJpaRepository;

    @Override
    public Image save(Image image) {
        return imageJpaRepository.save(ImageEntity.from(image)).toModel();
    }

    @Override
    public Page<Image> findAll(PageRequest pageRequest) {
        return imageJpaRepository.findAll(pageRequest).map(ImageEntity::toModel);
    }

    @Override
    public Image getByBoardId(Long boardId) {
        return findByBoardId(boardId).orElseThrow(
                () -> new ResourceNotFoundException("board", boardId)
        );
    }

    @Override
    public Optional<Image> findByBoardId(Long boardId) {
        return imageJpaRepository.findByBoardEntityId(boardId).map(ImageEntity::toModel);
    }

    @Override
    public Image getByBoardIdFetchJoinImage(Long boardId) {
        return findByBoardIdFetchJoinImage(boardId).orElseThrow(
                () -> new ResourceNotFoundException("board", boardId)
        );
    }

    @Override
    public Optional<Image> findByBoardIdFetchJoinImage(Long boardId) {
        return imageJpaRepository.findByBoardIdFetchJoinImage(boardId).map(ImageEntity::toModel);
    }

    @Override
    public Page<Image> findAllByFundraiserId(Long fundraiserId, PageRequest pageSortByCreatedAtDesc) {
        log.info("findAllByFundraiserId: {}", fundraiserId);
        return imageJpaRepository.findAllByBoardEntityFundraiserIdFetchJoinBoard(fundraiserId, pageSortByCreatedAtDesc).map(ImageEntity::toModel);
    }

}
