package io.nuabo.hikitty.board.infrastructure.entity;

import io.nuabo.common.infrastructure.BaseTimeEntity;
import io.nuabo.hikitty.board.domain.Image;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Entity(name = "image")
@SQLDelete(sql = "update image set delete_at=now() where id=?")
@Where(clause = "delete_at is null")
public class ImageEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity boardEntity;

    @Column(nullable = false)
    private String originalName;

    @Column(nullable = false)
    private String savedName;

    @Column(nullable = false)
    private String url;

    public static ImageEntity from(Image image) {
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.id = image.getId();
        imageEntity.boardEntity = BoardEntity.from(image.getBoard());
        imageEntity.originalName = image.getOriginalName();
        imageEntity.savedName = image.getSavedName();
        imageEntity.url = image.getUrl();
        return imageEntity;
    }

    public Image toModel() {
        return Image.builder()
                .id(id)
                .board(boardEntity.toModel())
                .originalName(originalName)
                .savedName(savedName)
                .url(url)
                .build();
    }
}
