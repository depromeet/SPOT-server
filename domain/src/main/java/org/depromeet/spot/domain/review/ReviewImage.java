package org.depromeet.spot.domain.review;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewImage {

    private final Long id;
    private final Long reviewId;
    private final String url;
    private final LocalDateTime createdAt;
    private final LocalDateTime deletedAt;

    public static ReviewImage of(Long reviewId, String url) {
        return ReviewImage.builder().reviewId(reviewId).url(url).build();
    }
}
