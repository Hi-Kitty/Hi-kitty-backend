package io.nuabo.hikitty.amazons3.application;

import com.amazonaws.services.s3.model.ObjectMetadata;
import io.nuabo.common.application.port.UuidHolder;
import io.nuabo.common.domain.exception.MultipartFileException;
import io.nuabo.common.domain.exception.ResourceNotFoundException;
import io.nuabo.hikitty.amazons3.domain.AmazonS3Upload;
import io.nuabo.hikitty.amazons3.infrastructure.FileNameImpl;
import io.nuabo.hikitty.amazons3.application.port.AmazonS3ClientHolder;
import io.nuabo.hikitty.amazons3.application.port.AmazonS3Repository;
import io.nuabo.hikitty.amazons3.application.port.ObjectMetadataHolder;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Builder
@Service
@RequiredArgsConstructor
public class AmazonS3Service {
    private final UuidHolder uuidHolder;

    private final AmazonS3Repository amazonS3Repository;

    private final ObjectMetadataHolder objectMetadataHolder;

    private final AmazonS3ClientHolder amazonS3ClientHolder;


    @Transactional
    public AmazonS3Upload upload(MultipartFile multipartFile) throws MultipartFileException {


        FileNameImpl fileNameImpl = FileNameImpl.builder()
                .multipartFile(multipartFile)
                .uuidHolder(uuidHolder)
                .build();

        String storeFilename = fileNameImpl.getStoreFilename();
        String key = fileNameImpl.getKeyName();
        String originalFilename = fileNameImpl.getOriginalFilename();


        InputStream inputStream;
        try {
            inputStream = multipartFile.getInputStream();
        } catch (IOException e) {
            throw new MultipartFileException("AmazonS3Service", multipartFile.getOriginalFilename());
        }

        ObjectMetadata objectMetadata = objectMetadataHolder.initAmazonS3Upload(multipartFile);

        amazonS3ClientHolder.saveAmazonS3FromAWS(objectMetadata, key, inputStream);
        String imgUrl = amazonS3ClientHolder.getAmazonS3FromAWS(key);

        AmazonS3Upload amazonS3Upload = AmazonS3Upload.from(originalFilename, storeFilename, imgUrl);
        return amazonS3Repository.save(amazonS3Upload);
    }

    @Transactional(readOnly = true)
    public AmazonS3Upload getById(Long id) {
        return amazonS3Repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("AmazonS3Service", id));
    }

    public AmazonS3Upload getByOriginalName(String originalName) {
        return amazonS3Repository.findByOriginName(originalName).orElseThrow(() -> new ResourceNotFoundException("AmazonS3Service", originalName));
    }
}
