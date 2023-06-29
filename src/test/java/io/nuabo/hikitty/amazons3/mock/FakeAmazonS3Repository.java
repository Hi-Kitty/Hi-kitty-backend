package io.nuabo.hikitty.amazons3.mock;

import io.nuabo.hikitty.amazons3.domain.AmazonS3Upload;
import io.nuabo.hikitty.amazons3.application.port.AmazonS3Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class FakeAmazonS3Repository implements AmazonS3Repository {
    private final AtomicLong autoGeneratedId = new AtomicLong(0);
    private final List<AmazonS3Upload> data = new ArrayList<>();

    @Override
    public AmazonS3Upload save(AmazonS3Upload amazonS3Upload) {
        if (amazonS3Upload.getId() == null || amazonS3Upload.getId() == 0) {
            AmazonS3Upload newAmazonS3Upload = AmazonS3Upload.builder()
                    .id(autoGeneratedId.incrementAndGet())
                    .originalName(amazonS3Upload.getOriginalName())
                    .savedName(amazonS3Upload.getSavedName())
                    .url(amazonS3Upload.getUrl())
                    .build();
            data.add(newAmazonS3Upload);
            return newAmazonS3Upload;
         }

        data.removeIf(item -> item.getId().equals(amazonS3Upload.getId()));
        data.add(amazonS3Upload);
        return amazonS3Upload;

    }

    @Override
    public Optional<AmazonS3Upload> findById(Long id) {
        return data.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<AmazonS3Upload> findByOriginName(String originalName) {
        return data.stream()
                .filter(item -> item.getOriginalName().equals(originalName))
                .findFirst();
    }


}
