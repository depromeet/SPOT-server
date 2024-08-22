package org.depromeet.spot.infrastructure.jpa.review.repository.scrap;

import org.depromeet.spot.infrastructure.jpa.review.entity.scrap.ReviewScrapEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface ReviewScrapJpaRepository extends JpaRepository<ReviewScrapEntity, Integer> {
    long countByReviewId(long reviewId);

    boolean existsByMemberIdAndReviewId(long memberId, long reviewId);

    @Modifying
    void deleteByMemberIdAndReviewId(long memberId, long reviewId);
}
