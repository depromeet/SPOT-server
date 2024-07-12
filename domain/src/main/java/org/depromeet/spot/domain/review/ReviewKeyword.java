package org.depromeet.spot.domain.review;

import lombok.Getter;

@Getter
public class ReviewKeyword {

    private final Long id;
    private final Long reviewId;
    private final Long keywordId;
    private final Boolean isPositive;

    public ReviewKeyword(Long id, Long reviewId, Long keywordId, Boolean isPositive) {
        this.id = id;
        this.reviewId = reviewId;
        this.keywordId = keywordId;
        this.isPositive = isPositive;
    }
}
