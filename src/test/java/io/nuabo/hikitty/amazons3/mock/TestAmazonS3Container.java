package io.nuabo.hikitty.amazons3.mock;

import io.nuabo.common.application.port.UuidHolder;
import io.nuabo.hikitty.amazons3.application.port.AWSConnection;
import io.nuabo.hikitty.amazons3.presentation.AmazonS3Controller;
import io.nuabo.hikitty.amazons3.application.AmazonS3ServiceImpl;
import io.nuabo.hikitty.amazons3.application.port.AmazonS3Repository;
import io.nuabo.hikitty.amazons3.presentation.port.AmazonS3Service;
import io.nuabo.hikitty.user.mock.FakeAwsConnection;
import io.nuabo.hikitty.user.mock.FakeObjectMetadataHolder;
import lombok.Builder;


public class TestAmazonS3Container {

    public final AmazonS3Repository amazonS3Repository;

    public final AmazonS3Controller amazonS3Controller;

    public final AmazonS3Service amazonS3Service;

    @Builder
    public TestAmazonS3Container(UuidHolder uuidHolder, String bucket) {

        FakeObjectMetadataHolder fakeObjectMetadataHolder = new FakeObjectMetadataHolder();
        FakeAmazonS3ClientHolder fakeAmazonS3ClientHolder = new FakeAmazonS3ClientHolder(bucket);
        AWSConnection fakeAwsConnection = new FakeAwsConnection(uuidHolder, fakeObjectMetadataHolder, fakeAmazonS3ClientHolder);

        this.amazonS3Repository = new FakeAmazonS3Repository();


        this.amazonS3Service = AmazonS3ServiceImpl.builder()
                .awsConnection(fakeAwsConnection)
                .amazonS3Repository(amazonS3Repository)
                .build();


        this.amazonS3Controller = AmazonS3Controller.builder()
                .amazonS3Service(amazonS3Service)
                .build();


    }
}
