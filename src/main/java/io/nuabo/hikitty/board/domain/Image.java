package io.nuabo.hikitty.board.domain;

import io.nuabo.hikitty.amazons3.domain.AmazonS3Upload;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Image {

    private final Long id;

    private final Board board;

    private final String originalName;
    private final String savedName;
    private final String url;

    @Builder
    public Image(Long id, Board board, String originalName, String savedName, String url) {
        this.id = id;
        this.board = board;
        this.originalName = originalName;
        this.savedName = savedName;
        this.url = url;
    }

    public static Image from(Board board, AmazonS3Upload amazonS3Upload) {
        return Image.builder()
                .board(board)
                .originalName(amazonS3Upload.getOriginalName())
                .savedName(amazonS3Upload.getSavedName())
                .url(amazonS3Upload.getUrl())
                .build();
    }
}
