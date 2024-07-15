package org.depromeet.spot.domain.review;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReviewKeyword {

    private final Long id;
    private final Long reviewId;
    private final Long keywordId;
    private final Boolean isPositive;
}
