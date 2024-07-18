package org.depromeet.spot.usecase.port.in.review;

import java.time.LocalDateTime;
import java.util.List;

import org.depromeet.spot.domain.review.Review;

import lombok.Builder;

public interface CreateReviewUsecase {

    Review create(Long seatId, Long memberId, CreateReviewCommand command);

    @Builder
    record CreateReviewCommand(
            List<String> images,
            List<String> good,
            List<String> bad,
            LocalDateTime dateTime,
            String content) {}
}
