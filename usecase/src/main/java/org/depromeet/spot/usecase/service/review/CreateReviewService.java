package org.depromeet.spot.usecase.service.review;

import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.usecase.port.in.review.CreateReviewUsecase;
import org.depromeet.spot.usecase.port.out.review.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateReviewService implements CreateReviewUsecase {

    private final ReviewRepository reviewRepository;

    @Override
    public Review create(final Long seatId, final Long memberId, CreateReviewCommand command) {
        return null;
    }
}
