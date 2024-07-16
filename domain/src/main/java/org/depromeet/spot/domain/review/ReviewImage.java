package org.depromeet.spot.domain.review;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReviewImage {

    private final Long id;
    private final Long reviewId;
    private final String url;
    private final LocalDateTime createdAt;
    private final LocalDateTime deletedAt;
}
