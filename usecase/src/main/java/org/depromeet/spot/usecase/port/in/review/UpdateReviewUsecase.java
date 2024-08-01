package org.depromeet.spot.usecase.port.in.review;

import java.time.LocalDateTime;
import java.util.List;

import org.depromeet.spot.usecase.port.in.review.CreateReviewUsecase.ReviewResult;

import lombok.Builder;

public interface UpdateReviewUsecase {
    ReviewResult updateReview(Long memberId, Long reviewId, UpdateReviewCommand command);

    @Builder
    record UpdateReviewCommand(
            Long blockId,
            String sectionName,
            Integer rowNumber,
            Integer seatNumber,
            List<String> images,
            List<String> good,
            List<String> bad,
            String content,
            LocalDateTime dateTime) {}
}
