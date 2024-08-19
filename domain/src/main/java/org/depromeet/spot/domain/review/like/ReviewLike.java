package org.depromeet.spot.domain.review.like;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ReviewLike {

    private final Long id;
    private final Long memberId;
    private final Long reviewId;
}
