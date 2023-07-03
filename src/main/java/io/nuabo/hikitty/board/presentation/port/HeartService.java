package io.nuabo.hikitty.board.presentation.port;

import io.nuabo.hikitty.board.domain.Heart;

public interface HeartService {
    Heart createHeart(Long boardId, String email);

    Heart deleteHeart(Long heartId);
}
