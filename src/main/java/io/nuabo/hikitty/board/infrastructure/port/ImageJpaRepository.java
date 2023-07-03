package io.nuabo.hikitty.board.infrastructure.port;

import io.nuabo.hikitty.board.infrastructure.entity.ImageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface ImageJpaRepository extends JpaRepository<ImageEntity, Long> {

    @Query("select i from image i join fetch i.boardEntity ORDER BY i.createAt DESC")
    Page<ImageEntity> findAll(Pageable pageable);

    Optional<ImageEntity> findByBoardEntityId(Long boardId);

    @Query("select i from image i join fetch i.boardEntity where i.boardEntity.id = :boardId")
    Optional<ImageEntity> findByBoardIdFetchJoinImage(Long boardId);

    @Query("select i from image i join fetch i.boardEntity where i.boardEntity.fundraiserId = :fundraiserId")
    Page<ImageEntity> findAllByBoardEntityFundraiserIdFetchJoinBoard(Long fundraiserId, Pageable page);
}
