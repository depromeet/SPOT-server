package org.depromeet.spot.domain.review.result;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KeywordCount {
    private final String content;
    private final Long count;
    private final Boolean isPositive;
}
