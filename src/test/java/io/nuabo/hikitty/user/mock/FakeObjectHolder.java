package io.nuabo.hikitty.user.mock;

import com.amazonaws.services.s3.model.ObjectMetadata;
import io.nuabo.common.application.port.ObjectHolder;
import org.springframework.web.multipart.MultipartFile;

public class FakeObjectHolder implements ObjectHolder {
    @Override
    public ObjectMetadata initAmazonS3Upload(MultipartFile multipartFile) {
        return null;
    }

}
