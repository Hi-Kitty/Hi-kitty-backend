package io.nuabo.common.application.port;

import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.web.multipart.MultipartFile;

public interface ObjectHolder {

    ObjectMetadata initAmazonS3Upload(MultipartFile multipartFile);

}
