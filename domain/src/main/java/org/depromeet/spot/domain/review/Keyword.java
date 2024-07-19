package org.depromeet.spot.domain.review;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Keyword {
    private final Long id;
    private final String content;
    private final Boolean isPositive;
}
