package org.depromeet.spot.usecase.service.review.like;

import org.depromeet.spot.common.exception.review.ReviewException.ReviewNotFoundException;
import org.depromeet.spot.domain.review.like.ReviewLike;
import org.depromeet.spot.usecase.port.in.review.ReadReviewUsecase;
import org.depromeet.spot.usecase.port.in.review.like.ReviewLikeUsecase;
import org.depromeet.spot.usecase.port.out.review.ReviewLikeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewLikeService implements ReviewLikeUsecase {

    private final ReadReviewUsecase readReviewUsecase;
    private final ReviewLikeRepository reviewLikeRepository;

    @Override
    public void toggleLike(final long memberId, final long reviewId) {
        checkValidLike(reviewId);

        if (reviewLikeRepository.existsBy(memberId, reviewId)) {
            cancelLike(memberId, reviewId);
            return;
        }

        addLike(memberId, reviewId);
    }

    private void checkValidLike(final long reviewId) {
        if (!readReviewUsecase.existById(reviewId)) {
            throw new ReviewNotFoundException();
        }
    }

    private void cancelLike(final long memberId, final long reviewId) {
        reviewLikeRepository.deleteBy(memberId, reviewId);
        // review의 likeCnt 감소
    }

    private void addLike(final long memberId, final long reviewId) {
        ReviewLike like = ReviewLike.builder().memberId(memberId).reviewId(reviewId).build();
        reviewLikeRepository.save(like);
        // review의 likeCnt 증가
    }
}
