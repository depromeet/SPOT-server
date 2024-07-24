package org.depromeet.spot.usecase.port.in.review;

public interface DeleteReviewUsecase {

    Long deleteReview(Long reviewId, Long memberId);
}
