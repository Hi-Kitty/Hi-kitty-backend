package io.nuabo.hikitty.amazons3.application.port;

import com.amazonaws.services.s3.model.ObjectMetadata;

import java.io.InputStream;

public interface AmazonS3ClientHolder {
    String getAmazonS3FromAWS(String keyName);
    void saveAmazonS3FromAWS(ObjectMetadata objectMetadata, String key, InputStream inputStream);
}
