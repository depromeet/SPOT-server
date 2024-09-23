package org.depromeet.spot.usecase.port.in.review.like;

public interface ReviewLikeUsecase {
    boolean toggleLike(Long memberId, long reviewId);
}
