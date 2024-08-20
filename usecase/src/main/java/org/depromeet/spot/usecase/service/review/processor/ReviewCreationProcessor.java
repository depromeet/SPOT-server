package org.depromeet.spot.usecase.service.review.processor;

import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.usecase.port.in.review.CreateReviewUsecase.CreateAdminReviewCommand;
import org.depromeet.spot.usecase.port.in.review.CreateReviewUsecase.CreateReviewCommand;

public interface ReviewCreationProcessor {
    Review createReview(Long blockId, Member member, CreateReviewCommand command);

    Review createAdminReview(
            long stadiumId,
            String blockCode,
            int rowNumber,
            Member member,
            CreateAdminReviewCommand command);
}
