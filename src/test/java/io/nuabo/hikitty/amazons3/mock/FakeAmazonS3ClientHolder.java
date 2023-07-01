package io.nuabo.hikitty.amazons3.mock;

import com.amazonaws.services.s3.model.ObjectMetadata;
import io.nuabo.hikitty.amazons3.application.port.AmazonS3ClientHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;

@Slf4j
@RequiredArgsConstructor
public class FakeAmazonS3ClientHolder implements AmazonS3ClientHolder {

    private final String bucket;
    @Override
    public String getAmazonS3FromAWS(String keyName) {
        return "https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/"+keyName;
    }

    @Override
    public void saveAmazonS3FromAWS(ObjectMetadata objectMetadata, String key, InputStream inputStream) {
            log.info("saveAmazonS3FromAWS - save 됨");
    }
}
