package io.nuabo.hikitty.board.application.port;

import io.nuabo.common.application.port.ClockHolder;
import io.nuabo.hikitty.board.domain.Image;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PageImageDto {
    private final Long id;

    private final Long dDay;
    private final String title;

    private final Long fundraiserId;
    private final String fundraiserName;
    private final String percent;

    private final Long imageId;
    private final String imageUrl;
    private final String imageName;

    private final LocalDateTime createdAt;

    private final LocalDateTime endAt;

    @Builder
    public PageImageDto(Long id, Long dDay, String title, Long fundraiserId, String fundraiserName, String percent, Long imageId, String imageUrl, String imageName, LocalDateTime createdAt, LocalDateTime endAt) {
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

    public static PageImageDto from(Image image, ClockHolder clockHolder) {
        return PageImageDto.builder()
                .id(image.getId())
                .dDay(clockHolder.calculateDDay(image.getBoard().getCreatedAt(), image.getBoard().getEndAt()))
                .title(image.getBoard().getTitle())
                .fundraiserId(image.getBoard().getFundraiserId())
                .fundraiserName(image.getBoard().getFundraiserName())
                .percent(clockHolder.calculateProgressPercentage(image.getBoard().getCurrentAmount(), image.getBoard().getTargetAmount()))
                .imageId(image.getId())
                .imageUrl(image.getUrl())
                .imageName(image.getOriginalName())
                .createdAt(image.getBoard().getCreatedAt())
                .endAt(image.getBoard().getEndAt())
                .build();

    }
}
