package io.nuabo.hikitty.board.domain;

import io.nuabo.hikitty.board.presentation.response.PlanResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class BoardFundraiserImagePlan {

    private final Board board;
    private final Fundraiser fundraiser;

    private final Image image;
    private final List<Plan> plans;


    @Builder
    public BoardFundraiserImagePlan(Board board, Fundraiser fundraiser, Image image, List<Plan> plans, List<PlanResponse> planResponse) {
        this.board = board;
        this.fundraiser = fundraiser;
        this.image = image;
        this.plans = plans;
    }

    public static BoardFundraiserImagePlan from(Board board, Fundraiser fundraiser, Image image, List<Plan> plans) {
        return BoardFundraiserImagePlan.builder()
                .board(board)
                .fundraiser(fundraiser)
                .image(image)
                .plans(plans)
                .build();
    }
}
