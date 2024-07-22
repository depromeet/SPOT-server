package org.depromeet.spot.application.review.dto.response;

import org.depromeet.spot.domain.review.image.ReviewImage;

public record ReviewImageResponse(Long id, String url) {
    public static ReviewImageResponse from(ReviewImage image) {
        return new ReviewImageResponse(image.getId(), image.getUrl());
    }
}
