package org.depromeet.spot.usecase.port.out.review;

import java.util.List;
import java.util.Map;

import org.depromeet.spot.domain.review.like.ReviewLike;

public interface ReviewLikeRepository {

    boolean existsBy(long memberId, long reviewId);

    long countByReview(long reviewId);

    void deleteBy(long memberId, long reviewId);

    void save(ReviewLike like);

    Map<Long, Boolean> existsByMemberIdAndReviewIds(Long memberId, List<Long> reviewIds);
}
