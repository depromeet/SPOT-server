package org.depromeet.spot.usecase.service.review.like;

import java.util.ConcurrentModificationException;

import org.depromeet.spot.domain.review.like.ReviewLike;
import org.depromeet.spot.usecase.port.in.review.ReadReviewUsecase;
import org.depromeet.spot.usecase.port.in.review.UpdateReviewUsecase;
import org.depromeet.spot.usecase.port.in.review.like.ReviewLikeUsecase;
import org.depromeet.spot.usecase.port.out.review.ReviewLikeRepository;
import org.depromeet.spot.usecase.port.out.review.ReviewRepository;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Builder
@Transactional
@RequiredArgsConstructor
public class ReviewLikeService implements ReviewLikeUsecase {

    private final ReadReviewUsecase readReviewUsecase;
    private final UpdateReviewUsecase updateReviewUsecase;
    private final ReviewLikeRepository reviewLikeRepository;
    private final ReviewRepository reviewRepository;

    @Override
    @Transactional
    @Retryable(
            retryFor = ObjectOptimisticLockingFailureException.class,
            maxAttempts = 4,
            backoff = @Backoff(delay = 100))
    public boolean toggleLike(final Long memberId, final long reviewId) {
        boolean exists = reviewLikeRepository.existsBy(memberId, reviewId);

        if (exists) {
            reviewLikeRepository.deleteBy(memberId, reviewId);
            reviewRepository.updateLikesCount(reviewId, false);
            return false;
        }

        ReviewLike like = ReviewLike.builder().memberId(memberId).reviewId(reviewId).build();
        reviewLikeRepository.save(like);
        reviewRepository.updateLikesCount(reviewId, true);
        return true;
    }

    // 예외 처리를 위한 Recovery 메소드
    @Recover
    public boolean recoverToggleLike(
            ObjectOptimisticLockingFailureException e, Long memberId, long reviewId) {
        log.error(
                "Failed to toggle like after 3 attempts for memberId: {} and reviewId: {}",
                memberId,
                reviewId,
                e);
        throw new ConcurrentModificationException("좋아요 처리 중 충돌이 발생했습니다. 잠시 후 다시 시도해주세요.");
    }
}
