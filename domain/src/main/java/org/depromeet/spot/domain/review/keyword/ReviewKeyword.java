package org.depromeet.spot.domain.review.keyword;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewKeyword {
    private final Long id;
    private final Long reviewId;
    private final Keyword keyword;
}
