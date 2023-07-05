package io.nuabo.common.infrastructure;

import com.amazonaws.services.s3.model.ObjectMetadata;
import io.nuabo.common.application.port.ObjectHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class SystemObjectHolder implements ObjectHolder {

    @Override
    public ObjectMetadata initAmazonS3Upload(MultipartFile multipartFile) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());
        return objectMetadata;
    }

}
