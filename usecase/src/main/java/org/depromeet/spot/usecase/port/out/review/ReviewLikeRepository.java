package org.depromeet.spot.usecase.port.out.review;

import org.depromeet.spot.domain.review.like.ReviewLike;

public interface ReviewLikeRepository {

    boolean existsBy(long memberId, long reviewId);

    long countByReview(long reviewId);

    void deleteBy(long memberId, long reviewId);

    void save(ReviewLike like);
}
