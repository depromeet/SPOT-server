package org.depromeet.spot.domain.review.image;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TopReviewImage {
    private final Long id;
    private final String url;
    private final Long reviewId;
    private final String blockCode;
    private final Integer rowNumber;
    private final Integer seatNumber;
}
