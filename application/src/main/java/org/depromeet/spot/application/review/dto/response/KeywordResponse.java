package org.depromeet.spot.application.review.dto.response;

import org.depromeet.spot.domain.review.Keyword;

public record KeywordResponse(Long id, String content, Boolean isPositive) {
    public static KeywordResponse from(Keyword keyword) {
        return new KeywordResponse(keyword.getId(), keyword.getContent(), keyword.isPositive());
    }
}
