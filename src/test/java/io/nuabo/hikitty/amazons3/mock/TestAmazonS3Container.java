package io.nuabo.hikitty.amazons3.mock;

import io.nuabo.common.application.port.UuidHolder;
import io.nuabo.hikitty.amazons3.presentation.AmazonS3Controller;
import io.nuabo.hikitty.amazons3.infrastructure.SystemObjectMetadataHolder;
import io.nuabo.hikitty.amazons3.application.AmazonS3Service;
import io.nuabo.hikitty.amazons3.application.port.AmazonS3Repository;
import lombok.Builder;


public class TestAmazonS3Container {

    public final AmazonS3Repository amazonS3Repository;

    public final AmazonS3Controller amazonS3Controller;

    public final AmazonS3Service amazonS3Service;

    @Builder
    public TestAmazonS3Container(UuidHolder uuidHolder, String bucket) {
        TestAmazonS3ClientHolder s3ClientHolder = new TestAmazonS3ClientHolder(bucket);


        this.amazonS3Repository = new FakeAmazonS3Repository();


        this.amazonS3Service = AmazonS3Service.builder()
                .amazonS3ClientHolder(s3ClientHolder)
                .amazonS3Repository(amazonS3Repository)
                .objectMetadataHolder(new SystemObjectMetadataHolder())
                .uuidHolder(uuidHolder)
                .build();


        this.amazonS3Controller = AmazonS3Controller.builder()
                .amazonS3Service(amazonS3Service)
                .build();


    }
}
