package io.nuabo.hikitty.user.mock;

import com.amazonaws.services.s3.model.ObjectMetadata;
import io.nuabo.hikitty.amazons3.application.port.ObjectMetadataHolder;
import org.springframework.web.multipart.MultipartFile;

public class FakeObjectMetadataHolder implements ObjectMetadataHolder {
    @Override
    public ObjectMetadata initAmazonS3Upload(MultipartFile multipartFile) {
        return null;
    }
}
