package io.nuabo.hikitty.amazons3.infrastructure;

import com.amazonaws.services.s3.model.ObjectMetadata;
import io.nuabo.hikitty.amazons3.application.port.ObjectMetadataHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class SystemObjectMetadataHolder implements ObjectMetadataHolder {

    @Override
    public ObjectMetadata initAmazonS3Upload(MultipartFile multipartFile) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());
        return objectMetadata;
    }
}
