package io.nuabo.hikitty.amazons3.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AmazonS3JpaRepository extends JpaRepository<AmazonS3Entity, Long> {

    Optional<AmazonS3Entity> findByOriginalName(String originalName);
}
