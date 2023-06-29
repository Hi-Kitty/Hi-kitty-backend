package io.nuabo.hikitty.amazons3.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AmazonS3Upload {

    private final Long id;
    private final String originalName;
    private final String savedName;
    private final String url;

    @Builder
    public AmazonS3Upload(Long id, String originalName, String savedName, String url) {
        this.id = id;
        this.originalName = originalName;
        this.savedName = savedName;
        this.url = url;
    }

    public static AmazonS3Upload from(String originalFilename, String savedFilename, String imgUrl) {
       return AmazonS3Upload.builder()
                .originalName(originalFilename)
                .savedName(savedFilename)
                .url(imgUrl)
                .build();
    }

}
