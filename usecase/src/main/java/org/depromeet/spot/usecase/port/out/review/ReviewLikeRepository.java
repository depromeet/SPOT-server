package org.depromeet.spot.usecase.port.out.review;

import java.util.List;
import java.util.Map;

import org.depromeet.spot.domain.review.like.ReviewLike;

public interface ReviewLikeRepository {

    boolean existsBy(Long memberId, Long reviewId);

    long countByReview(long reviewId);

    void deleteBy(Long memberId, Long reviewId);

    void save(ReviewLike like);

    Map<Long, Boolean> existsByMemberIdAndReviewIds(Long memberId, List<Long> reviewIds);
}
