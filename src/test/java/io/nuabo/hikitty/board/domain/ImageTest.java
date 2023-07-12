package io.nuabo.hikitty.board.domain;

import io.nuabo.hikitty.amazons3.domain.AmazonS3Upload;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ImageTest {

    @Test
    @DisplayName("Image를 Board와 AmazonS3Upload을 이용하여 생성한다.")
    void from() {
        // given
        Board board = Board.builder()
                .id(1L)
                .build();
        AmazonS3Upload amazonS3Upload = AmazonS3Upload.builder()
                .originalName("originalName")
                .savedName("savedName")
                .url("url")
                .build();
        // when
        Image image = Image.from(board, amazonS3Upload);
        // then
        assertAll(
                () -> assertEquals(image.getBoard(), board),
                () -> assertEquals(image.getOriginalName(), "originalName"),
                () -> assertEquals(image.getSavedName(), "savedName"),
                () -> assertEquals(image.getUrl(), "url")
        );

    }
}