package org.depromeet.spot.usecase.port.in.review;

import java.time.LocalDateTime;
import java.util.List;

import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.domain.review.Review.ReviewType;

import lombok.Builder;

public interface UpdateReviewUsecase {
    UpdateReviewResult updateReview(Long memberId, Long reviewId, UpdateReviewCommand command);

    void updateLikesCount(Review review);

    void updateScrapsCount(Review review);

    void updateviewCount(Long reviewId);

    @Builder
    record UpdateReviewCommand(
            Long stadiumId,
            Long blockId,
            Integer rowNumber,
            Integer seatNumber,
            List<String> images,
            List<String> good,
            List<String> bad,
            String content,
            LocalDateTime dateTime,
            ReviewType reviewType) {}

    record UpdateReviewResult(Review review) {}
}
