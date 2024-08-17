package org.depromeet.spot.infrastructure.jpa.review.repository.like;

import org.depromeet.spot.infrastructure.jpa.review.entity.like.ReviewLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface ReviewLikeJpaRepository extends JpaRepository<ReviewLikeEntity, Long> {

    long countByReviewId(long reviewId);

    boolean existsByMemberIdAndReviewId(long memberId, long reviewId);

    @Modifying
    void deleteByMemberIdAndReviewId(long memberId, long reviewId);
}
