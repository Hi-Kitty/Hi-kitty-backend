package io.nuabo.hikitty.amazons3.application;

import io.nuabo.common.domain.exception.ResourceNotFoundException;
import io.nuabo.hikitty.amazons3.application.port.AWSConnection;
import io.nuabo.hikitty.amazons3.domain.AmazonS3Upload;
import io.nuabo.hikitty.amazons3.application.port.AmazonS3Repository;
import io.nuabo.hikitty.amazons3.presentation.port.AmazonS3Service;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Service
@RequiredArgsConstructor
public class AmazonS3ServiceImpl implements AmazonS3Service {
    private final AmazonS3Repository amazonS3Repository;

    private final AWSConnection awsConnection;

    @Transactional
    public AmazonS3Upload upload(MultipartFile multipartFile) {
        AmazonS3Upload amazonS3Upload = awsConnection.sendFileToAWS(multipartFile);
        return amazonS3Repository.save(amazonS3Upload);
    }


    @Transactional(readOnly = true)
    public AmazonS3Upload getById(Long id) {
        return amazonS3Repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("AmazonS3ServiceImpl", id));
    }

    public AmazonS3Upload getByOriginalName(String originalName) {
        return amazonS3Repository.findByOriginName(originalName).orElseThrow(() -> new ResourceNotFoundException("AmazonS3ServiceImpl", originalName));
    }
}
