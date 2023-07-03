package io.nuabo.hikitty.board.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class BoardFundraiserImagePlan {

    private final Board board;
    private final Image image;
    private final List<Plan> plans;


    @Builder
    public BoardFundraiserImagePlan(Board board, Image image, List<Plan> plans) {
        this.board = board;
        this.image = image;
        this.plans = plans;
    }

    public static BoardFundraiserImagePlan from(Board board,  Image image, List<Plan> plans) {
        return BoardFundraiserImagePlan.builder()
                .board(board)
                .image(image)
                .plans(plans)
                .build();
    }
}
