package io.nuabo.hikitty.amazons3.infrastructure;

import com.amazonaws.services.s3.model.ObjectMetadata;
import io.nuabo.common.application.port.UuidHolder;
import io.nuabo.common.domain.exception.MultipartFileException;
import io.nuabo.hikitty.amazons3.application.port.AWSConnection;
import io.nuabo.hikitty.amazons3.application.port.AmazonS3ClientHolder;
import io.nuabo.hikitty.amazons3.application.port.ObjectMetadataHolder;
import io.nuabo.hikitty.amazons3.domain.AmazonS3Upload;
import io.nuabo.hikitty.amazons3.domain.FileName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Component
@RequiredArgsConstructor
public class AWSConnectionImpl implements AWSConnection {

    private final UuidHolder uuidHolder;

    private final ObjectMetadataHolder objectMetadataHolder;

    private final AmazonS3ClientHolder amazonS3ClientHolder;


    @Override
    public AmazonS3Upload sendFileToAWS(MultipartFile multipartFile) {

        FileName fileName = FileName.from(multipartFile, uuidHolder);


        String storeFilename = fileName.getStoreFilename();
        String key = fileName.getKeyName();
        String originalFilename = fileName.getOriginalFilename();


        InputStream inputStream;
        try {
            inputStream = multipartFile.getInputStream();
        } catch (IOException e) {
            throw new MultipartFileException("AmazonS3ServiceImpl", multipartFile.getOriginalFilename());
        }

        ObjectMetadata objectMetadata = objectMetadataHolder.initAmazonS3Upload(multipartFile);

        amazonS3ClientHolder.saveAmazonS3FromAWS(objectMetadata, key, inputStream);
        String imgUrl = amazonS3ClientHolder.getAmazonS3FromAWS(key);

        return AmazonS3Upload.from(originalFilename, storeFilename, imgUrl);
    }
}
