package io.nuabo.hikitty.board.presentation.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PageBoardRequest {

    @Schema(description = "페이지",  example = "0")
    private final int page;

    @Schema(description = "사이즈",  example = "10")
    private final int size;

    @Builder
    public PageBoardRequest(int page, int size) {
        this.page = page;
        this.size = size;
    }


}
