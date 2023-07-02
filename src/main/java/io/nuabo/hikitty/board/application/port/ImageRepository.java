package io.nuabo.hikitty.board.application.port;

import io.nuabo.hikitty.board.domain.Image;

public interface ImageRepository {
    Image save(Image image);
}
