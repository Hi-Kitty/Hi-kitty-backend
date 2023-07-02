package io.nuabo.hikitty.board.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Doner {

    private final Long id;

    private final Heart heart;

    private final Long donerId;

    private final String name;

    private final String profileName;

    private final String profileUrl;


    @Builder
    public Doner(Long id, Heart heart, Long donerId, String name, String profileName, String profileUrl) {
        this.id = id;
        this.heart = heart;
        this.donerId = donerId;
        this.name = name;
        this.profileName = profileName;
        this.profileUrl = profileUrl;
    }
}
