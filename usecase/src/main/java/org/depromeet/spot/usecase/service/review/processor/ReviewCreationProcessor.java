package org.depromeet.spot.usecase.service.review.processor;

import java.util.Map;

import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.domain.review.keyword.Keyword;
import org.depromeet.spot.usecase.port.in.review.CreateReviewUsecase.CreateAdminReviewCommand;
import org.depromeet.spot.usecase.port.in.review.CreateReviewUsecase.CreateReviewCommand;
import org.depromeet.spot.usecase.port.in.review.UpdateReviewUsecase.UpdateReviewCommand;

public interface ReviewCreationProcessor {
    Review createReview(Long blockId, Member member, CreateReviewCommand command);

    Review createAdminReview(
            long stadiumId,
            String blockCode,
            int rowNumber,
            Member member,
            CreateAdminReviewCommand command);

    Map<Long, Keyword> processReviewDetails(Review review, CreateReviewCommand command);

    Map<Long, Keyword> processReviewDetails(Review review, UpdateReviewCommand command);

    Map<Long, Keyword> processAdminReviewDetails(Review review, CreateAdminReviewCommand command);

    Review updateReviewData(Review existingReview, UpdateReviewCommand command);
}
