package io.nuabo.hikitty.amazons3.presentation.port;
import io.nuabo.hikitty.amazons3.domain.AmazonS3Upload;
import org.springframework.web.multipart.MultipartFile;

public interface AmazonS3Service {
    AmazonS3Upload upload(MultipartFile multipartFile);

    AmazonS3Upload getById(Long id);

    AmazonS3Upload getByOriginalName(String originalName);
}
