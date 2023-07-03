package io.nuabo.hikitty.board.application.port;

import io.nuabo.hikitty.board.domain.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface ImageRepository {
    Image save(Image image);

    Page<Image> findAll(PageRequest pageRequest);
}
