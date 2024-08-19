package org.depromeet.spot.usecase.port.in.review;

import java.time.LocalDateTime;
import java.util.List;

import org.depromeet.spot.domain.review.Review;

import lombok.Builder;

public interface UpdateReviewUsecase {
    UpdateReviewResult updateReview(Long memberId, Long reviewId, UpdateReviewCommand command);

    void updateLikesCount(Review review);

    @Builder
    record UpdateReviewCommand(
            Long blockId,
            Integer seatNumber,
            List<String> images,
            List<String> good,
            List<String> bad,
            String content,
            LocalDateTime dateTime) {}

    record UpdateReviewResult(Review review) {}
}
