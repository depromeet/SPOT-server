package org.depromeet.spot.infrastructure.jpa.review.repository.scrap;

import java.util.List;

import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.domain.review.Review.SortCriteria;
import org.depromeet.spot.domain.review.scrap.ReviewScrap;
import org.depromeet.spot.infrastructure.jpa.review.entity.ReviewEntity;
import org.depromeet.spot.infrastructure.jpa.review.entity.scrap.ReviewScrapEntity;
import org.depromeet.spot.usecase.port.out.review.ReviewScrapRepository;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReviewScrapRepositoryImpl implements ReviewScrapRepository {

    private final ReviewScrapJpaRepository reviewScrapJpaRepository;
    private final ReviewScrapCustomRepository reviewScrapCustomRepository;

    @Override
    public List<Review> findScrappedReviewsByMemberId(
            Long memberId,
            Long stadiumId,
            List<Integer> months,
            List<String> good,
            List<String> bad,
            String cursor,
            SortCriteria sortBy,
            Integer size) {
        List<ReviewEntity> reviewEntities =
                reviewScrapCustomRepository.findScrappedReviewsByMemberId(
                        memberId, stadiumId, months, good, bad, cursor, sortBy, size);

        return reviewEntities.stream().map(ReviewEntity::toDomain).toList();
    }

    @Override
    public Long getTotalCount(
            Long memberId,
            Long stadiumId,
            List<Integer> months,
            List<String> good,
            List<String> bad) {
        return reviewScrapCustomRepository.getTotalCount(memberId, stadiumId, months, good, bad);
    }

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
