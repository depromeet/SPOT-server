package org.depromeet.spot.application.review.dto.response;

import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.usecase.port.in.review.ReadReviewUsecase.MyRecentReviewResult;

import lombok.Builder;

@Builder
public record MyRecentReviewResponse(MyReviewResponse review, long reviewCount) {
    public static MyRecentReviewResponse from(MyRecentReviewResult result) {
        return MyRecentReviewResponse.builder()
                .review(MyReviewResponse.from(result.review()))
                .reviewCount(result.reviewCount())
                .build();
    }

    public record MyReviewResponse(
            BaseReviewResponse baseReview,
            String stadiumName,
            String sectionName,
            String blockCode) {
        public static MyReviewResponse from(Review review) {
            return new MyReviewResponse(
                    BaseReviewResponse.from(review),
                    review.getStadium().getName(),
                    review.getSection().getName(),
                    review.getBlock().getCode());
        }
    }
}
