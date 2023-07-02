package io.nuabo.hikitty.board.infrastructure;

import io.nuabo.hikitty.board.application.port.ImageRepository;
import io.nuabo.hikitty.board.domain.Image;
import io.nuabo.hikitty.board.infrastructure.entity.ImageEntity;
import io.nuabo.hikitty.board.infrastructure.port.ImageJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ImageRepositoryImpl implements ImageRepository {

    private final ImageJpaRepository imageJpaRepository;

    @Override
    public Image save(Image image) {
        return imageJpaRepository.save(ImageEntity.from(image)).toModel();
    }

}
