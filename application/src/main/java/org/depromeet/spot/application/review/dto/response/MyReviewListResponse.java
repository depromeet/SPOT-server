package org.depromeet.spot.application.review.dto.response;

import java.util.List;
import java.util.stream.Collectors;

import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.domain.review.result.MyReviewListResult;

public record MyReviewListResponse(
        List<MyReviewResponse> reviews, long totalElements, int totalPages, int number, int size) {
    public static MyReviewListResponse from(MyReviewListResult result) {
        List<MyReviewResponse> reviews =
                result.getReviews().stream()
                        .map(MyReviewResponse::from)
                        .collect(Collectors.toList());

        return new MyReviewListResponse(
                reviews,
                result.getTotalElements(),
                result.getTotalPages(),
                result.getNumber(),
                result.getSize());
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
