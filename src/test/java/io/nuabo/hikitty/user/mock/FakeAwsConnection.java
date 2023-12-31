package io.nuabo.hikitty.user.mock;

import com.amazonaws.services.s3.model.ObjectMetadata;
import io.nuabo.common.application.port.UuidHolder;
import io.nuabo.hikitty.amazons3.application.port.AWSConnection;
import io.nuabo.hikitty.amazons3.application.port.AmazonS3ClientHolder;
import io.nuabo.common.application.port.ObjectHolder;
import io.nuabo.hikitty.amazons3.domain.AmazonS3Upload;
import io.nuabo.hikitty.amazons3.domain.FileName;
import org.springframework.web.multipart.MultipartFile;

public class FakeAwsConnection implements AWSConnection {

    private final UuidHolder uuidHolder;

    private final ObjectHolder objectHolder;

    private final AmazonS3ClientHolder amazonS3ClientHolder;
    public FakeAwsConnection(UuidHolder uuidHolder, ObjectHolder objectHolder, AmazonS3ClientHolder amazonS3ClientHolder) {
        this.uuidHolder = uuidHolder;
        this.objectHolder = objectHolder;
        this.amazonS3ClientHolder = amazonS3ClientHolder;
    }

    @Override
    public AmazonS3Upload sendFileToAWS(MultipartFile multipartFile) {
        FileName fileName = FileName.from(multipartFile, uuidHolder);

        String storeFilename = fileName.getStoreFilename();
        String key = fileName.getKeyName();
        String originalFilename = fileName.getOriginalFilename();
        ObjectMetadata objectMetadata = objectHolder.initAmazonS3Upload(multipartFile);

        amazonS3ClientHolder.saveAmazonS3FromAWS(objectMetadata, key, null);
        String imgUrl = amazonS3ClientHolder.getAmazonS3FromAWS(key);

        return AmazonS3Upload.from(originalFilename, storeFilename, imgUrl);
    }
}
