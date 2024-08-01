package org.depromeet.spot.usecase.port.in.review;

import java.time.LocalDateTime;
import java.util.List;

import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.domain.seat.Seat;

import lombok.Builder;

public interface CreateReviewUsecase {
    ReviewResult create(
            Long blockId, Integer seatNumber, Long memberId, CreateReviewCommand command);

    @Builder
    record CreateReviewCommand(
            List<String> images,
            List<String> good,
            List<String> bad,
            String content,
            LocalDateTime dateTime) {}

    record ReviewResult(Review review, Member member, Seat seat) {}
}
