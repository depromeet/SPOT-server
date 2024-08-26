package org.depromeet.spot.usecase.service.review.like;

import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.domain.review.like.ReviewLike;
import org.depromeet.spot.usecase.port.in.review.ReadReviewUsecase;
import org.depromeet.spot.usecase.port.in.review.UpdateReviewUsecase;
import org.depromeet.spot.usecase.port.in.review.like.ReviewLikeUsecase;
import org.depromeet.spot.usecase.port.out.review.ReviewLikeRepository;
import org.depromeet.spot.usecase.service.util.MixpanelUtil;
import org.depromeet.spot.usecase.service.util.MixpanelUtil.MixpanelEvent;
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
    private final MixpanelUtil mixpanelUtil;

    // TODO: 분산락 적용 예정
    @Override
    public void toggleLike(final long memberId, final long reviewId) {
        Review review = readReviewUsecase.findById(reviewId);

        if (reviewLikeRepository.existsBy(memberId, reviewId)) {
            cancelLike(memberId, reviewId, review);
            return;
        }

        addLike(memberId, reviewId, review);

        // 믹스패널 이벤트(좋아요 수) 발생
        mixpanelUtil.track(MixpanelEvent.REVIEW_LIKE_COUNT, String.valueOf(memberId));
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
