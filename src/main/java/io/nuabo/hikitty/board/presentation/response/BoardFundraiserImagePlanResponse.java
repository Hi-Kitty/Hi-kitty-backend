package io.nuabo.hikitty.board.presentation.response;

import io.nuabo.hikitty.board.domain.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class BoardFundraiserImagePlanResponse {

    private final Long id;
    private final String title;

    private final String subTitle;

    private final String content;

    private final Long targetAmount;

    private final Long currentAmount;

    private final LocalDateTime endAt;

    private final LocalDateTime createAt;

    private final Long fundraiserId;

    private final String name;

    private final String profileName;

    private final String profileUrl;

    private final String ImageOriginalName;

    private final String ImageUrl;


    private final List<PlanResponse> planResponse;

    @Builder
    public BoardFundraiserImagePlanResponse(Long id, String title, String subTitle, String content, Long targetAmount, Long currentAmount, LocalDateTime endAt, LocalDateTime createAt, Long fundraiserId, String name, String profileName, String profileUrl, String imageOriginalName, String imageUrl, List<PlanResponse> planResponse) {
        this.id = id;
        this.title = title;
        this.subTitle = subTitle;
        this.content = content;
        this.targetAmount = targetAmount;
        this.currentAmount = currentAmount;
        this.endAt = endAt;
        this.createAt = createAt;
        this.fundraiserId = fundraiserId;
        this.name = name;
        this.profileName = profileName;
        this.profileUrl = profileUrl;
        this.ImageOriginalName = imageOriginalName;
        this.ImageUrl = imageUrl;
        this.planResponse = planResponse;
    }
    public static BoardFundraiserImagePlanResponse from(BoardFundraiserImagePlan boardFundraiserImagePlan) {
        return BoardFundraiserImagePlanResponse.builder()
                .id(boardFundraiserImagePlan.getBoard().getId())
                .title(boardFundraiserImagePlan.getBoard().getTitle())
                .subTitle(boardFundraiserImagePlan.getBoard().getSubTitle())
                .content(boardFundraiserImagePlan.getBoard().getContent())
                .targetAmount(boardFundraiserImagePlan.getBoard().getTargetAmount())
                .currentAmount(boardFundraiserImagePlan.getBoard().getCurrentAmount())
                .endAt(boardFundraiserImagePlan.getBoard().getEndAt())
                .createAt(boardFundraiserImagePlan.getBoard().getCreatedAt())
                .fundraiserId(boardFundraiserImagePlan.getBoard().getFundraiserId())
                .name(boardFundraiserImagePlan.getBoard().getFundraiserName())
                .profileName(boardFundraiserImagePlan.getBoard().getFundraiserProfileName())
                .profileUrl(boardFundraiserImagePlan.getBoard().getFundraiserProfileUrl())
                .imageOriginalName(boardFundraiserImagePlan.getImage().getOriginalName())
                .imageUrl(boardFundraiserImagePlan.getImage().getUrl())
                .planResponse(PlanResponse.from(boardFundraiserImagePlan.getPlans()))
                .build();
    }
}
