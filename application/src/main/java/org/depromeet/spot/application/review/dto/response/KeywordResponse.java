package org.depromeet.spot.application.review.dto.response;

import org.depromeet.spot.domain.review.keyword.ReviewKeyword;

public record KeywordResponse(Long id, String content, Boolean isPositive) {

    public static KeywordResponse from(ReviewKeyword reviewKeyword) {
        return new KeywordResponse(
                reviewKeyword.getKeyword().getId(),
                reviewKeyword.getKeyword().getContent(),
                reviewKeyword.getKeyword().getIsPositive());
    }
}
