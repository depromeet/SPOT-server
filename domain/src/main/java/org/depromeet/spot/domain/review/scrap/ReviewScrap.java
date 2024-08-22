package org.depromeet.spot.domain.review.scrap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ReviewScrap {
    private final Long id;
    private final Long memberId;
    private final Long reviewId;
}
