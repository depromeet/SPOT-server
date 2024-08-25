package org.depromeet.spot.usecase.service.review.like;

import org.depromeet.spot.common.annotation.DistributedLock;
import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.domain.review.like.ReviewLike;
import org.depromeet.spot.usecase.port.in.review.ReadReviewUsecase;
import org.depromeet.spot.usecase.port.in.review.UpdateReviewUsecase;
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
    private final UpdateReviewUsecase updateReviewUsecase;
    private final ReviewLikeRepository reviewLikeRepository;

    @Override
    @DistributedLock(key = "#reviewId")
    public void toggleLike(final long memberId, final long reviewId) {
        Review review = readReviewUsecase.findById(reviewId);

        if (reviewLikeRepository.existsBy(memberId, reviewId)) {
            cancelLike(memberId, reviewId, review);
            return;
        }

        addLike(memberId, reviewId, review);
    }

    public void cancelLike(final long memberId, final long reviewId, Review review) {
        reviewLikeRepository.deleteBy(memberId, reviewId);
        review.cancelLike();
        updateReviewUsecase.updateLikesCount(review);
    }

    public void addLike(final long memberId, final long reviewId, Review review) {
        ReviewLike like = ReviewLike.builder().memberId(memberId).reviewId(reviewId).build();
        reviewLikeRepository.save(like);
        review.addLike();
        updateReviewUsecase.updateLikesCount(review);
    }
}
