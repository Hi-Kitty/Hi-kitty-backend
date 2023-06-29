package io.nuabo.hikitty.amazons3.domain;

import io.nuabo.hikitty.mock.TestUuidHolder;
import io.nuabo.hikitty.amazons3.mock.FakeFileName;
import io.nuabo.hikitty.amazons3.mock.TestAmazonS3ClientHolder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class AmazonS3UploadTest {


    @Test
    @DisplayName("AmazonS3Upload는 AmazonS3UploadCreate 객체로 생성할 수 있다.")
    void from() {
        // given
        TestUuidHolder uuidHolder = new TestUuidHolder("aaaaaaa-aaaa-aaaa-aaaaaaaaaaaa");
        FakeFileName fileName = new FakeFileName("originalFilename", uuidHolder);

        String bucket = "nuabo-bucket";

        String storeFilename = fileName.getStoreFilename();
        String originalFilename = fileName.getOriginalFilename();
        String keyName = fileName.getKeyName();
        TestAmazonS3ClientHolder s3ClientHolder = new TestAmazonS3ClientHolder(bucket);
        String imgUrl = s3ClientHolder.getAmazonS3FromAWS(keyName);

        // when
        AmazonS3Upload s3Upload = AmazonS3Upload.from(originalFilename, storeFilename, imgUrl);

        // then
        log.info("s3Upload getSavedFilename: {}", s3Upload.getSavedName());
        log.info("s3Upload getImgUrl: {}", s3Upload.getUrl());
        log.info("s3Upload getOriginalFilename: {}", s3Upload.getOriginalName());
        assertThat(s3Upload.getId()).isNull();
        assertThat(s3Upload.getUrl()).isEqualTo(imgUrl);
        assertThat(s3Upload.getSavedName()).isEqualTo(storeFilename);
        assertThat(s3Upload.getOriginalName()).isEqualTo(originalFilename);
    }
}