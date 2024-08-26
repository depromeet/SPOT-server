package org.depromeet.spot.usecase.service.review.processor;

import java.util.List;

import org.depromeet.spot.domain.review.Review;

public interface ReadReviewProcessor {

    void setLikedAndScrappedStatus(List<Review> reviews, Long memberId);
}
