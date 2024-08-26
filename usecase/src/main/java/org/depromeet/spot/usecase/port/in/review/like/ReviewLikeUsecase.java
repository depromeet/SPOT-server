package org.depromeet.spot.usecase.port.in.review.like;

public interface ReviewLikeUsecase {
    void toggleLike(Long memberId, long reviewId);
}
