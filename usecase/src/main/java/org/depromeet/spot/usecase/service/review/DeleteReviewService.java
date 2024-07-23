package org.depromeet.spot.usecase.service.review;

import org.depromeet.spot.usecase.port.in.review.DeleteReviewUsecase;
import org.depromeet.spot.usecase.port.out.review.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteReviewService implements DeleteReviewUsecase {

    private final ReviewRepository reviewRepository;

    @Override
    @Transactional
    public Long deleteReview(Long reviewId, Long memberId) {
        return reviewRepository.softDeleteByIdAndMemberId(reviewId, memberId);
    }
}
