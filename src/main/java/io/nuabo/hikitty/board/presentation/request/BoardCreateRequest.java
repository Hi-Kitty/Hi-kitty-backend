package io.nuabo.hikitty.board.presentation.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class BoardCreateRequest {

    @NotNull(message = "title cannot be null")
    @Schema(description = "title",  example = "제목을 입력해주세요.")
    @Size(min=1, max=255, message = "제목은 1자 이상 255자 이하로 입력해주세요.")
    private final String title;

    @Schema(description = "subTitle",  example = "부제목을 입력해주세요 - 조회하기에서 보여서 만듬!")
    @Size(min=1, max=255, message = "부제목은 1자 이상 255자 이하로 입력해주세요.")
    private final String subTitle;

    @NotNull(message = "content cannot be null")
    @Schema(description = "content",  example = "내용을 입력해주세요. - 길게 적어주세용(4GB)")
    private final String content;

    @NotNull(message = "targetAmount cannot be null,  more than 1000")
    @Min(value = 1000, message = "목표금액은 1000원 이상이어야 합니다.")
    @Schema(description = "targetAmount",  example = "1000")
    private final Long targetAmount;

    @NotNull(message = "endAt cannot be null, future is possible")
    @Future(message = "종료일은 현재보다 미래여야 합니다.")
    @Schema(description = "endAt",  example = "종료일을 입력해주세요.")
    private final LocalDate endAt;


    @Builder
    public BoardCreateRequest(
            @JsonProperty("title") String title,
            @JsonProperty("subTitle") String subTitle,
            @JsonProperty("content") String content,
            @JsonProperty("targetAmount") Long targetAmount,
            @JsonProperty("endAt") LocalDate endAt) {
        this.title = title;
        this.subTitle = subTitle;
        this.content = content;
        this.targetAmount = targetAmount;
        this.endAt = endAt;
    }
}
