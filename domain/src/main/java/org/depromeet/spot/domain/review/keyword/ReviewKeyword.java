package org.depromeet.spot.domain.review.keyword;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ReviewKeyword {
    private final Long id;
    private final Long keywordId;

    public static ReviewKeyword create(Long id, Long keywordId) {
        return new ReviewKeyword(id, keywordId);
    }
}
