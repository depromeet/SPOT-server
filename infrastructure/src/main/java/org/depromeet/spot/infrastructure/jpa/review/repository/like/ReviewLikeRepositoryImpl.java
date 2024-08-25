package org.depromeet.spot.infrastructure.jpa.review.repository.like;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.depromeet.spot.domain.review.like.ReviewLike;
import org.depromeet.spot.infrastructure.jpa.review.entity.like.ReviewLikeEntity;
import org.depromeet.spot.usecase.port.out.review.ReviewLikeRepository;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReviewLikeRepositoryImpl implements ReviewLikeRepository {

    private final ReviewLikeJpaRepository reviewLikeJpaRepository;

    @Override
    public boolean existsBy(final long memberId, final long reviewId) {
        return reviewLikeJpaRepository.existsByMemberIdAndReviewId(memberId, reviewId);
    }

    @Override
    public long countByReview(final long reviewId) {
        return reviewLikeJpaRepository.countByReviewId(reviewId);
    }

    @Override
    public void deleteBy(final long memberId, final long reviewId) {
        reviewLikeJpaRepository.deleteByMemberIdAndReviewId(memberId, reviewId);
    }

    @Override
    public void save(ReviewLike like) {
        ReviewLikeEntity entity = ReviewLikeEntity.from(like);
        reviewLikeJpaRepository.save(entity);
    }

    @Override
    public Map<Long, Boolean> existsByMemberIdAndReviewIds(Long memberId, List<Long> reviewIds) {
        List<Long> likedReviewIds =
                reviewLikeJpaRepository.findReviewIdsByMemberIdAndReviewIdIn(memberId, reviewIds);
        return reviewIds.stream()
                .collect(Collectors.toMap(reviewId -> reviewId, likedReviewIds::contains));
    }
}
