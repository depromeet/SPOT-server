package org.depromeet.spot.domain.review;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class ReviewImage {

    private final Long id;
    private final Long reviewId;
    private final String url;
    private final LocalDateTime createdAt;
    private final String status;

    public ReviewImage(Long id, Long reviewId, String url, LocalDateTime createdAt, String status) {
        this.id = id;
        this.reviewId = reviewId;
        this.url = url;
        this.createdAt = createdAt;
        this.status = status;
    }
}
