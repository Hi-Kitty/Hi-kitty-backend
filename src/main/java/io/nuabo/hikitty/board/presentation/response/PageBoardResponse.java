package io.nuabo.hikitty.board.presentation.response;

import io.nuabo.hikitty.board.domain.Image;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PageBoardResponse {

    private final Long id;

    private Long dDay;
    private final String title;

    private final Long fundraiserId;
    private final String fundraiserName;
    private final Long percent;

    private final Long imageId;
    private final String imageUrl;
    private final String imageName;

    private final LocalDateTime createdAt;

    private final LocalDateTime endAt;

    @Builder
    public PageBoardResponse(Long id, Long dDay, String title, Long fundraiserId, String fundraiserName, Long percent, Long imageId, String imageUrl, String imageName, LocalDateTime createdAt, LocalDateTime endAt) {
        this.id = id;
        this.dDay = dDay;
        this.title = title;
        this.fundraiserId = fundraiserId;
        this.fundraiserName = fundraiserName;
        this.percent = percent;
        this.imageId = imageId;
        this.imageUrl = imageUrl;
        this.imageName = imageName;
        this.createdAt = createdAt;
        this.endAt = endAt;
    }

    public static PageBoardResponse from(Image image) {
        return null;
    }
}
