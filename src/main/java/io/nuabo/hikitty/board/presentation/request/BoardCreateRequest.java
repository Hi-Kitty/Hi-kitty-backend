package io.nuabo.hikitty.board.presentation.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardCreateRequest {

    @NotNull(message = "title cannot be null")
    @Schema(description = "title",  example = "제목을 입력해주세요.")
    private final String title;

    @Schema(description = "subTitle",  example = "부제목을 입력해주세요 - 조회하기에서 보여서 만듬!")
    private final String subTitle;

    @NotNull(message = "content cannot be null")
    @Schema(description = "content",  example = "내용을 입력해주세요.")
    private final String content;

    @NotNull(message = "targetAmount cannot be null")
    @Schema(description = "targetAmount",  example = "목표금액을 입력해주세요.")
    private final Long targetAmount;

    @NotNull(message = "endAt cannot be null")
    @Schema(description = "endAt",  example = "종료일을 입력해주세요.")
    private final LocalDateTime endAt;


    @Builder
    public BoardCreateRequest(
            @JsonProperty("title") String title,
            @JsonProperty("subTitle") String subTitle,
            @JsonProperty("content") String content,
            @JsonProperty("targetAmount") Long targetAmount,
            @JsonProperty("endAt") LocalDateTime endAt) {
        this.title = title;
        this.subTitle = subTitle;
        this.content = content;
        this.targetAmount = targetAmount;
        this.endAt = endAt;
    }
}
