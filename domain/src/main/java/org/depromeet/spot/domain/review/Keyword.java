package org.depromeet.spot.domain.review;

import lombok.Getter;

@Getter
public class Keyword {

    private final Long id;
    private final String content;

    public Keyword(Long id, String content) {
        this.id = id;
        this.content = content;
    }
}
