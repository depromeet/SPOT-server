package org.depromeet.spot.infrastructure.jpa.review.repository.scrap;

import org.depromeet.spot.domain.review.scrap.ReviewScrap;
import org.depromeet.spot.infrastructure.jpa.review.entity.scrap.ReviewScrapEntity;
import org.depromeet.spot.usecase.port.out.review.ReviewScrapRepository;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReviewScrapRepositoryImpl implements ReviewScrapRepository {

    private final ReviewScrapJpaRepository reviewScrapJpaRepository;

    @Override
    public boolean existsBy(final long memberId, final long reviewId) {
        return reviewScrapJpaRepository.existsByMemberIdAndReviewId(memberId, reviewId);
    }

    @Override
    public long countByReview(final long reviewId) {
        return reviewScrapJpaRepository.countByReviewId(reviewId);
    }

    @Override
    public void deleteBy(final long memberId, final long reviewId) {
        reviewScrapJpaRepository.deleteByMemberIdAndReviewId(memberId, reviewId);
    }

    @Override
    public void save(ReviewScrap scrap) {
        ReviewScrapEntity entity = ReviewScrapEntity.from(scrap);
        reviewScrapJpaRepository.save(entity);
    }
}
