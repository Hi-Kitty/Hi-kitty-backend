package io.nuabo.hikitty.amazons3.presentation.response;

import io.nuabo.hikitty.amazons3.domain.AmazonS3Upload;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AmazonS3Response {

    private Long id;
    private String originName;
    private String url;

    public static AmazonS3Response from(AmazonS3Upload amazonS3Upload) {
        return AmazonS3Response.builder()
                .id(amazonS3Upload.getId())
                .originName(amazonS3Upload.getOriginalName())
                .url(amazonS3Upload.getUrl())
                .build();
    }
}
