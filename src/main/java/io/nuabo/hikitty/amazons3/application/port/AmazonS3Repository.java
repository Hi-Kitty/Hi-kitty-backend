package io.nuabo.hikitty.amazons3.application.port;


import io.nuabo.hikitty.amazons3.domain.AmazonS3Upload;

import java.util.Optional;

public interface AmazonS3Repository {
    AmazonS3Upload save(AmazonS3Upload amazonS3Upload);

    Optional<AmazonS3Upload> findById(Long id);

    Optional<AmazonS3Upload> findByOriginName(String originalName);
}
