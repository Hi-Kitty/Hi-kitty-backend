package io.nuabo.hikitty.board.domain;

import io.nuabo.common.application.port.ClockHolder;
import io.nuabo.common.application.port.DefaultImageConfig;
import io.nuabo.hikitty.board.presentation.response.HeartResponse;
import io.nuabo.hikitty.board.presentation.response.PlanResponse;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ImagePlanHeartGet {
    private final Long id;
    private final String title;

    private final Long percent;

    private final String subTitle;

    private final String content;

    private final Long targetAmount;

    private final Long currentAmount;

    private final LocalDateTime endAt;

    private final LocalDateTime createAt;

    private final Long fundraiserId;

    private final String fundraiserName;

    private final String profileName;

    private final String profileUrl;

    private final String ImageOriginalName;

    private final String ImageUrl;

    private final List<PlanResponse> planResponse;

    private final List<HeartResponse> heartResponses;

    @Builder

    public ImagePlanHeartGet(Long id, String title, Long percent, String subTitle, String content, Long targetAmount, Long currentAmount, LocalDateTime endAt, LocalDateTime createAt, Long fundraiserId, String fundraiserName, String profileName, String profileUrl, String imageOriginalName, String imageUrl, List<PlanResponse> planResponse, List<HeartResponse> heartResponses) {
        this.id = id;
        this.title = title;
        this.percent = percent;
        this.subTitle = subTitle;
        this.content = content;
        this.targetAmount = targetAmount;
        this.currentAmount = currentAmount;
        this.endAt = endAt;
        this.createAt = createAt;
        this.fundraiserId = fundraiserId;
        this.fundraiserName = fundraiserName;
        this.profileName = profileName;
        this.profileUrl = profileUrl;
        ImageOriginalName = imageOriginalName;
        ImageUrl = imageUrl;
        this.planResponse = planResponse;
        this.heartResponses = heartResponses;
    }

    public static ImagePlanHeartGet from(Image image, List<Plan> plans, List<Heart> hearts) {
        return ImagePlanHeartGet.builder()
                .id(image.getId())
                .title(image.getBoard().getTitle())
                .subTitle(image.getBoard().getSubTitle())
                .content(image.getBoard().getContent())
                .targetAmount(image.getBoard().getTargetAmount())
                .currentAmount(image.getBoard().getCurrentAmount())
                .endAt(image.getBoard().getEndAt())
                .createAt(image.getBoard().getCreatedAt())
                .fundraiserId(image.getBoard().getFundraiserId())
                .fundraiserName(image.getBoard().getFundraiserName())
                .profileName(image.getBoard().getFundraiserProfileName())
                .profileUrl(image.getBoard().getFundraiserProfileUrl())
                .imageOriginalName(image.getOriginalName())
                .imageUrl(image.getUrl())
                .planResponse(PlanResponse.from(plans))
                .heartResponses(HeartResponse.from(hearts))
                .build();
    }
    public static ImagePlanHeartGet from(Image image, List<Plan> plans, List<Heart> hearts, DefaultImageConfig defaultImageConfig, ClockHolder clockHolder) {
        return ImagePlanHeartGet.builder()
                .id(image.getId())
                .title(image.getBoard().getTitle())
                .subTitle(image.getBoard().getSubTitle())
                .content(image.getBoard().getContent())
                .targetAmount(image.getBoard().getTargetAmount())
                .currentAmount(image.getBoard().getCurrentAmount())
                .endAt(image.getBoard().getEndAt())
                .createAt(image.getBoard().getCreatedAt())
                .fundraiserId(image.getBoard().getFundraiserId())
                .fundraiserName(image.getBoard().getFundraiserName())
                .profileName(image.getBoard().getFundraiserProfileName() == null ? defaultImageConfig.getDefaultImageFundraiserOriginalName() : image.getBoard().getFundraiserProfileName())
                .profileUrl(image.getBoard().getFundraiserProfileUrl() == null ? defaultImageConfig.getDefaultImageFundraiserUrl() : image.getBoard().getFundraiserProfileUrl())
                .imageOriginalName(image.getOriginalName())
                .imageUrl(image.getUrl())
                .planResponse(PlanResponse.from(plans))
                .heartResponses(HeartResponse.from(hearts, defaultImageConfig))
                .percent(clockHolder.calculateProgressPercentage(image.getBoard().getCurrentAmount(), image.getBoard().getTargetAmount()))
                .build();
    }
}
