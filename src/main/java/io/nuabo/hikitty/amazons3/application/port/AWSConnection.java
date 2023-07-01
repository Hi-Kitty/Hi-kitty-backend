package io.nuabo.hikitty.amazons3.application.port;

import io.nuabo.hikitty.amazons3.domain.AmazonS3Upload;
import org.springframework.web.multipart.MultipartFile;

public interface AWSConnection {
    AmazonS3Upload sendFileToAWS(MultipartFile multipartFile);
}
