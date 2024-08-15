package org.depromeet.spot.usecase.port.in.review;

import java.time.LocalDateTime;
import java.util.List;

import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.domain.seat.Seat;
import org.springframework.web.multipart.MultipartFile;

import lombok.Builder;

public interface CreateReviewUsecase {
    CreateReviewResult create(
            Long blockId, Integer seatNumber, Long memberId, CreateReviewCommand command);

    void createAdmin(long blockId, int rowNumber, Long memberId, CreateAdminReviewCommand command);

    @Builder
    record CreateReviewCommand(
            List<String> images,
            List<String> good,
            List<String> bad,
            String content,
            LocalDateTime dateTime) {}

    @Builder
    record CreateAdminReviewCommand(
            List<MultipartFile> images,
            List<String> good,
            List<String> bad,
            String content,
            Integer seatNumber,
            LocalDateTime dateTime) {}

    record CreateReviewResult(Review review, Member member, Seat seat) {}
}
