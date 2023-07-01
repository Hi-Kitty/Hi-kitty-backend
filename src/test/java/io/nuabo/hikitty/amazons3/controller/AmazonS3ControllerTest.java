package io.nuabo.hikitty.amazons3.controller;


import io.nuabo.common.application.port.UuidHolder;
import io.nuabo.common.domain.exception.ResourceNotFoundException;
import io.nuabo.common.domain.utils.ApiUtils;
import io.nuabo.hikitty.amazons3.presentation.response.AmazonS3Response;
import io.nuabo.hikitty.amazons3.domain.AmazonS3Upload;
import io.nuabo.hikitty.mock.TestUuidHolder;
import io.nuabo.hikitty.amazons3.mock.TestAmazonS3Container;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@Slf4j
class AmazonS3ControllerTest {

    @Test
    @DisplayName("AmazonS3Controller에서 UploadFile을 저장한다.")
    void saveUploadFile() throws IOException {

        // given
        UuidHolder uuidHolder = new TestUuidHolder("aaaaaaa-aaaa-aaaa-aaaaaaaaaaaa");
        TestAmazonS3Container container = TestAmazonS3Container.builder()
                .bucket("nuabo-bucket")
                .uuidHolder(uuidHolder)
                .build();

        String fileName = "testImg";
        String contentType = "jpg";
        String filePath = "src/test/resources/img/testImg.png";
        MockMultipartFile getMockMultipartFile = getMockMultipartFile(fileName, contentType, filePath);


        ResponseEntity<ApiUtils.ApiResult<AmazonS3Response>> result = container.amazonS3Controller.upload(getMockMultipartFile);
        assertThat(result.getBody().getResponse().getUrl()).isEqualTo("https://nuabo-bucket.s3.ap-northeast-2.amazonaws.com/nuabo/aaaaaaa-aaaa-aaaa-aaaaaaaaaaaa.jpg");
        assertThat(result.getBody().getResponse().getOriginName()).isEqualTo("testImg.jpg");
        assertThat(result.getBody().getResponse().getId()).isEqualTo(1L);

    }

    @Test
    @DisplayName("AmazonS3Controller에서 id값으로 AmazonS3Upload 객체를 조회할 수 있다.")
    void getById() {
        // given
        UuidHolder uuidHolder = new TestUuidHolder("aaaaaaa-aaaa-aaaa-aaaaaaaaaaaa");
        TestAmazonS3Container container = TestAmazonS3Container.builder()
                .bucket("nuabo-bucket")
                .uuidHolder(uuidHolder)
                .build();

            container.amazonS3Repository.save(
                AmazonS3Upload.builder()
                        .id(1L)
                        .originalName("originalFilename")
                        .savedName("savedFilename")
                        .url("imgUrl")
                        .build()
        );
        // when
        ResponseEntity<ApiUtils.ApiResult<AmazonS3Response>> result = container.amazonS3Controller.getById(1L);

        // then
        assertThat(result.getBody().getResponse().getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("AmazonS3Controller에서 id값이 없을시 AmazonS3Upload 객체를 조회할 수 없다.")
    void getByIdException() {
        TestAmazonS3Container container = TestAmazonS3Container.builder()
                .build();

        // when then
        assertThatThrownBy(() -> {
            container.amazonS3Controller.getById(1L);
        }).isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("AmazonS3ServiceImpl에서 ID 1를 찾을 수 없습니다.");
    }


    @Test
    @DisplayName("AmazonS3Controller에서 originalName값으로 AmazonS3Upload 객체를 조회할 수 있다.")
    void getByOriginalName() {
        // given
        UuidHolder uuidHolder = new TestUuidHolder("aaaaaaa-aaaa-aaaa-aaaaaaaaaaaa");
        TestAmazonS3Container container = TestAmazonS3Container.builder()
                .bucket("nuabo-bucket")
                .uuidHolder(uuidHolder)
                .build();

            container.amazonS3Repository.save(
                AmazonS3Upload.builder()
                        .id(1L)
                        .originalName("originalFilename")
                        .savedName("savedFilename")
                        .url("imgUrl")
                        .build()
        );
        // when
        ResponseEntity<ApiUtils.ApiResult<AmazonS3Response>> result = container.amazonS3Controller.getByOriginalName("originalFilename");

        // then
        assertThat(result.getBody().getResponse().getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("AmazonS3Controller에서 originalName값이 없을시 AmazonS3Upload 객체를 조회할 수 없다.")
    void getByOriginalNameException() {
        TestAmazonS3Container container = TestAmazonS3Container.builder()
                .build();

        // when then
        assertThatThrownBy(() -> {
            container.amazonS3Controller.getByOriginalName("test.img");
        }).isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("AmazonS3ServiceImpl에서 data: test.img를 찾을 수 없습니다.");
    }




    private MockMultipartFile getMockMultipartFile(String fileName, String contentType, String path) throws IOException, IOException {
        FileInputStream fileInputStream = new FileInputStream(path);
        return new MockMultipartFile(fileName, fileName + "." + contentType, contentType, fileInputStream);
    }
}