package org.depromeet.spot.application.review.dto.response;

import org.depromeet.spot.domain.review.ReviewKeyword;

public record ReviewKeywordResponse(Long id, String content, Boolean isPositive) {
    public static ReviewKeywordResponse from(ReviewKeyword keyword) {
        return new ReviewKeywordResponse(
                keyword.getId(), keyword.getContent(), keyword.getIsPositive());
    }
}
