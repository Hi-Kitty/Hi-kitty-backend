package io.nuabo.hikitty.board.presentation.response;

import io.nuabo.common.application.port.DefaultImageConfig;
import io.nuabo.hikitty.board.domain.Heart;
import io.nuabo.hikitty.board.domain.Status;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class HeartResponse {
    private final Long id;

    private final Long donerId;

    private final String donerName;

    private final String donerProfileName;

    private final String donerProfileUrl;

    private final Status status;

    @Builder
    public HeartResponse(Long id, Long donerId, String donerName, String donerProfileName, String donerProfileUrl, Status status) {
        this.id = id;
        this.donerId = donerId;
        this.donerName = donerName;
        this.donerProfileName = donerProfileName;
        this.donerProfileUrl = donerProfileUrl;
        this.status = status;
    }

    public static HeartResponse from(Heart heart) {
        return HeartResponse.builder()
                .id(heart.getId())
                .donerId(heart.getDonerId())
                .donerName(heart.getDonerName())
                .donerProfileName(heart.getDonerProfileName())
                .donerProfileUrl(heart.getDonerProfileUrl())
                .status(heart.getStatus())
                .build();
    }
    public static HeartResponse from(Heart heart, DefaultImageConfig defaultImageConfig) {
        return HeartResponse.builder()
                .id(heart.getId())
                .donerId(heart.getDonerId())
                .donerName(heart.getDonerName())
                .donerProfileName(heart.getDonerProfileName() == null ? defaultImageConfig.getDefaultImageDonerOriginalName() : heart.getDonerProfileName())
                .donerProfileUrl(heart.getDonerProfileUrl() == null ? defaultImageConfig.getDefaultImageDonerUrl() : heart.getDonerProfileUrl())
                .status(heart.getStatus())
                .build();
    }

    public static List<HeartResponse> from(List<Heart> heart) {
        return heart.stream().map(HeartResponse::from).toList();
    }

    public static List<HeartResponse> from(List<Heart> heart, DefaultImageConfig defaultImageConfig) {
        return heart.stream().map(item -> from(item, defaultImageConfig)).toList();
    }
}
