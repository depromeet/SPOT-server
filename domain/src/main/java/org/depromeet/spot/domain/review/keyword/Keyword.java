package org.depromeet.spot.domain.review.keyword;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Keyword {
    private final Long id;
    private final String content;
    private final Boolean isPositive;

    public static Keyword create(Long id, String content, boolean isPositive) {
        return new Keyword(id, content, isPositive);
    }
}
