package io.nuabo.hikitty.amazons3.infrastructure;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import io.nuabo.common.domain.exception.SaveAmazonS3Exception;
import io.nuabo.hikitty.amazons3.application.port.AmazonS3ClientHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
@RequiredArgsConstructor
public class SystemAmazonS3ClientHolder implements AmazonS3ClientHolder {


    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public String getAmazonS3FromAWS(String keyName) {
        return amazonS3Client.getUrl(bucket, keyName).toString();
    }

    @Override
    public void saveAmazonS3FromAWS(ObjectMetadata objectMetadata, String key, InputStream inputStream) {
        try {
            amazonS3Client.putObject(new PutObjectRequest(bucket, key, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (Exception e) {
            throw new SaveAmazonS3Exception(e);
        }
    }
}
