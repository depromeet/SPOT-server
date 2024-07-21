package org.depromeet.spot.domain.review.image;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewImage {
    private final Long id;
    private final Long reviewId;
    private final String url;
}
