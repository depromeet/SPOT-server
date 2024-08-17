package org.depromeet.spot.usecase.port.out.review;

public interface ReviewLikeRepository {

    boolean existsBy(long memberId, long reviewId);

    long countByReview(long reviewId);

    void deleteBy(long memberId, long reviewId);
}
