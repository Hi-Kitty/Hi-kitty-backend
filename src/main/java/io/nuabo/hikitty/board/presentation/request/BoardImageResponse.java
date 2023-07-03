package io.nuabo.hikitty.board.presentation.request;

import io.nuabo.hikitty.board.domain.Plan;
import io.nuabo.hikitty.board.presentation.response.HeartResponse;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class BoardImageResponse {
    private final Long id;

    private final Long dDay;

    private final String title;

    private final String subTitle;

    private final String content;

    private final Long fundraiserId;

    private final String fundraiserName;

    private final String percent;

    private final Long imageId;

    private final String imageUrl;

    private final String imageName;

    private final LocalDateTime createdAt;

    private final LocalDateTime endAt;

    private final List<Plan> plans;

    private final Page<HeartResponse> heartResponses;

    @Builder
    public BoardImageResponse(Long id, Long dDay, String title, String subTitle, String content, Long fundraiserId, String fundraiserName, String percent, Long imageId, String imageUrl, String imageName, LocalDateTime createdAt, LocalDateTime endAt, List<Plan> plans, Page<HeartResponse> heartResponses) {
        this.id = id;
        this.dDay = dDay;
        this.title = title;
        this.subTitle = subTitle;
        this.content = content;
        this.fundraiserId = fundraiserId;
        this.fundraiserName = fundraiserName;
        this.percent = percent;
        this.imageId = imageId;
        this.imageUrl = imageUrl;
        this.imageName = imageName;
        this.createdAt = createdAt;
        this.endAt = endAt;
        this.plans = plans;
        this.heartResponses = heartResponses;
    }
}
