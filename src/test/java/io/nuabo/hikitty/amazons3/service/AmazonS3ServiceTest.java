package io.nuabo.hikitty.amazons3.service;

import io.nuabo.common.domain.exception.ResourceNotFoundException;
import io.nuabo.hikitty.amazons3.domain.AmazonS3Upload;
import io.nuabo.hikitty.amazons3.infrastructure.SystemObjectMetadataHolder;
import io.nuabo.hikitty.amazons3.application.AmazonS3Service;
import io.nuabo.hikitty.mock.TestUuidHolder;
import io.nuabo.hikitty.amazons3.mock.FakeAmazonS3Repository;
import io.nuabo.hikitty.amazons3.mock.FakeFileName;
import io.nuabo.hikitty.amazons3.mock.TestAmazonS3ClientHolder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
class AmazonS3ServiceTest {

    private AmazonS3Service amazonS3Service;

    @BeforeEach
    void init() {

        // given
        String bucket = "nuabo-bucket";
        TestAmazonS3ClientHolder s3ClientHolder = new TestAmazonS3ClientHolder(bucket);
        FakeAmazonS3Repository amazonS3Repository = new FakeAmazonS3Repository();
        TestUuidHolder uuidHolder = new TestUuidHolder("aaaaaaa-aaaa-aaaa-aaaaaaaaaaaa");

        amazonS3Service = AmazonS3Service.builder()
                .amazonS3ClientHolder(s3ClientHolder)
                .amazonS3Repository(amazonS3Repository)
                .objectMetadataHolder(new SystemObjectMetadataHolder())
                .uuidHolder(uuidHolder)
                .build();

        // case 1

        FakeFileName fileName = new FakeFileName("originalFilename", uuidHolder);
        String storeFilename = fileName.getStoreFilename();
        String originalFilename = fileName.getOriginalFilename();
        String keyName = fileName.getKeyName();
        String imgUrl = s3ClientHolder.getAmazonS3FromAWS(keyName);

        amazonS3Repository.save(
                AmazonS3Upload.builder()
                        .id(1L)
                        .originalName(originalFilename)
                        .savedName(storeFilename)
                        .url(imgUrl)
                        .build()
        );

        // case 2

        FakeFileName fileName2 = new FakeFileName("originalFilename2", uuidHolder);
        String storeFilename2 = fileName2.getStoreFilename();
        String originalFilename2 = fileName2.getOriginalFilename();
        String keyName2 = fileName2.getKeyName();
        String imgUrl2 = s3ClientHolder.getAmazonS3FromAWS(keyName2);

        amazonS3Repository.save(
                AmazonS3Upload.builder()
                        .id(2L)
                        .originalName(originalFilename2)
                        .savedName(storeFilename2)
                        .url(imgUrl2)
                        .build()
        );
    }
    @Test
    @DisplayName("AmazonS3Service는 AmazonS3Upload 객체를 업로드할 수 있다.")
    void upload() throws IOException {

        // given
        String fileName = "testImg";
        String contentType = "jpg";
        String filePath = "src/test/resources/img/testImg.png";

        MockMultipartFile getMockMultipartFile = getMockMultipartFile(fileName, contentType, filePath);

        // when
        AmazonS3Upload upload = amazonS3Service.upload(getMockMultipartFile);

        // then
        log.info("upload getSavedFilename= {}", upload.getSavedName());
        log.info("upload getOriginalFilename = {}", upload.getOriginalName());
        log.info("upload getImgUrl = {}", upload.getUrl());

        assertThat(upload.getSavedName()).isEqualTo("aaaaaaa-aaaa-aaaa-aaaaaaaaaaaa.jpg");
        assertThat(upload.getOriginalName()).isEqualTo("testImg.jpg");
        assertThat(upload.getUrl()).isEqualTo("https://nuabo-bucket.s3.ap-northeast-2.amazonaws.com/nuabo/aaaaaaa-aaaa-aaaa-aaaaaaaaaaaa.jpg");

    }

    @Test
    @DisplayName("AmazonS3Service는 id값으로 AmazonS3Upload 객체를 조회할 수 있다.")
    public void getById() {
        // given
        Long id = 1L;

        // when
        AmazonS3Upload result = amazonS3Service.getById(id);


        // then
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("AmazonS3Service는 id값을 찾을 때 없을 시 예외 처리가 일어난다.")
    public void getByIdException() {
        Long id = 3L;

        // when then
        assertThatThrownBy(() -> {
            amazonS3Service.getById(id);
        }).isInstanceOf(ResourceNotFoundException.class)
        .hasMessage("AmazonS3Service에서 ID 3를 찾을 수 없습니다.");
    }
    @Test
    @DisplayName("AmazonS3Service는 originalName값으로 AmazonS3Upload 객체를 조회할 수 있다.")
    public void getByOriginalName() {
        // given
        String name = "originalFilename";

        // when
        AmazonS3Upload result = amazonS3Service.getByOriginalName(name);


        // then
        assertThat(result.getOriginalName()).isEqualTo(name);
    }

    @Test
    @DisplayName("AmazonS3Service는 originalName값으로 찾을 때 없을 시 예외 처리가 일어난다.")
    public void getByOriginalNameException() {

        String name = "originalFilename333";
        // when then
        assertThatThrownBy(() -> {
            amazonS3Service.getByOriginalName(name);
        }).isInstanceOf(ResourceNotFoundException.class)
        .hasMessage("AmazonS3Service에서 data: originalFilename333를 찾을 수 없습니다.");
    }




    private MockMultipartFile getMockMultipartFile(String fileName, String contentType, String path) throws IOException, IOException {
        FileInputStream fileInputStream = new FileInputStream(new File(path));
        return new MockMultipartFile(fileName, fileName + "." + contentType, contentType, fileInputStream);
    }


}