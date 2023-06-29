package io.nuabo.hikitty.amazons3.infrastructure;

import io.nuabo.common.infrastructure.BaseTimeEntity;
import io.nuabo.hikitty.amazons3.domain.AmazonS3Upload;
import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "amazon_s3")
@SQLDelete(sql = "update amazon_s3 set delete_at=now() where id=?")
@Where(clause = "delete_at is null")

public class AmazonS3Entity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originalName;

    private String savedName;

    private String url;

    public static AmazonS3Entity from(AmazonS3Upload amazonS3Upload) {
        AmazonS3Entity amazonS3Entity = new AmazonS3Entity();
        amazonS3Entity.id = amazonS3Upload.getId();
        amazonS3Entity.originalName = amazonS3Upload.getOriginalName();
        amazonS3Entity.savedName = amazonS3Upload.getSavedName();
        amazonS3Entity.url = amazonS3Upload.getUrl();
        return amazonS3Entity;
    }

    public AmazonS3Upload toModel() {
        return AmazonS3Upload.builder()
                .id(id)
                .originalName(originalName)
                .savedName(savedName)
                .url(url)
                .build();
    }
}
