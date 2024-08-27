package org.depromeet.spot.infrastructure.jpa.review.repository.like;

import java.util.List;

import org.depromeet.spot.infrastructure.jpa.review.entity.like.ReviewLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewLikeJpaRepository extends JpaRepository<ReviewLikeEntity, Long> {

    long countByReviewId(long reviewId);

    boolean existsByMemberIdAndReviewId(long memberId, long reviewId);

    @Modifying
    void deleteByMemberIdAndReviewId(long memberId, long reviewId);

    @Query(
            "SELECT r.reviewId FROM ReviewLikeEntity r WHERE r.memberId = :memberId AND r.reviewId IN :reviewIds")
    List<Long> findReviewIdsByMemberIdAndReviewIdIn(
            @Param("memberId") Long memberId, @Param("reviewIds") List<Long> reviewIds);
}
