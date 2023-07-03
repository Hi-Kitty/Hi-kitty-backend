package io.nuabo.hikitty.board.application.port;

import io.nuabo.hikitty.board.domain.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public interface ImageRepository {
    Image save(Image image);

    Page<Image> findAll(PageRequest pageRequest);

    Image getByBoardId(Long boardId);

    Optional<Image> findByBoardId(Long boardId);

    Image getByBoardIdFetchJoinImage(Long boardId);

    Optional<Image> findByBoardIdFetchJoinImage(Long boardId);

    Page<Image> findAllByFundraiserId(Long fundraiserId, PageRequest pageSortByCreatedAtDesc);
}
