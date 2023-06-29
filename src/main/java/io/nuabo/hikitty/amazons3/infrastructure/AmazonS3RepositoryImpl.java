package io.nuabo.hikitty.amazons3.infrastructure;

import io.nuabo.hikitty.amazons3.domain.AmazonS3Upload;
import io.nuabo.hikitty.amazons3.application.port.AmazonS3Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AmazonS3RepositoryImpl implements AmazonS3Repository {

    private final AmazonS3JpaRepository amazonS3JpaRepository;
    @Override
    public AmazonS3Upload save(AmazonS3Upload amazonS3Upload) {
        return amazonS3JpaRepository.save(AmazonS3Entity.from(amazonS3Upload)).toModel();
    }

    @Override
    public Optional<AmazonS3Upload> findById(Long id) {
        return amazonS3JpaRepository.findById(id).map(AmazonS3Entity::toModel);
    }

    @Override
    public Optional<AmazonS3Upload> findByOriginName(String originalName) {
        return amazonS3JpaRepository.findByOriginalName(originalName).map(AmazonS3Entity::toModel);
    }

}
