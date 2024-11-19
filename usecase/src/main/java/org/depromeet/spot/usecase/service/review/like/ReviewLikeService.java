package org.depromeet.spot.usecase.service.review.like;

import java.util.ConcurrentModificationException;

import org.depromeet.spot.domain.review.like.ReviewLike;
import org.depromeet.spot.usecase.port.in.review.ReadReviewUsecase;
import org.depromeet.spot.usecase.port.in.review.UpdateReviewUsecase;
import org.depromeet.spot.usecase.port.in.review.like.ReviewLikeUsecase;
import org.depromeet.spot.usecase.port.out.review.ReviewLikeRepository;
import org.depromeet.spot.usecase.port.out.review.ReviewRepository;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Service
@Builder
@Transactional
@RequiredArgsConstructor
public class ReviewLikeService implements ReviewLikeUsecase {

    private final ReadReviewUsecase readReviewUsecase;
    private final UpdateReviewUsecase updateReviewUsecase;
    private final ReviewLikeRepository reviewLikeRepository;
    private final ReviewRepository reviewRepository;
    private static final int MAX_RETRY_COUNT = 3;

    @Override
    @Transactional
    public boolean toggleLike(final Long memberId, final long reviewId) {
        int retryCount = 0;
        while (retryCount < MAX_RETRY_COUNT) {
            try {
                return tryToggleLike(memberId, reviewId);
            } catch (ObjectOptimisticLockingFailureException e) {
                retryCount++;
                if (retryCount == MAX_RETRY_COUNT) {
                    throw new ConcurrentModificationException(
                            "좋아요 처리 중 충돌이 발생했습니다. 잠시 후 다시 시도해주세요.");
                }
                // 잠시 대기 후 재시도
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("처리가 중단되었습니다.", ie);
                }
            }
        }

        return false;
    }

    private boolean tryToggleLike(final Long memberId, final long reviewId) {
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
}
