package io.nuabo.hikitty.board.domain;

import lombok.Getter;

@Getter
public class Image {

    private final Long id;

    private final Board board;

    private final String originalName;
    private final String savedName;
    private final String url;

    public Image(Long id, Board board, String originalName, String savedName, String url) {
        this.id = id;
        this.board = board;
        this.originalName = originalName;
        this.savedName = savedName;
        this.url = url;
    }
}
