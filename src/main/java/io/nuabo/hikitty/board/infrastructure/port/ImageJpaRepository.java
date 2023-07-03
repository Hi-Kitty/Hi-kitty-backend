package io.nuabo.hikitty.board.infrastructure.port;

import io.nuabo.hikitty.board.infrastructure.entity.ImageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface ImageJpaRepository extends JpaRepository<ImageEntity, Long> {

    @Query("select i from image i join fetch i.boardEntity ORDER BY i.createAt DESC")
    Page<ImageEntity> findAll(Pageable pageable);
}
