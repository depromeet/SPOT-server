package org.depromeet.spot.usecase.service.review.scrap;

import org.depromeet.spot.domain.review.scrap.ReviewScrap;
import org.depromeet.spot.usecase.port.in.review.scrap.ReviewScrapUsecase;
import org.depromeet.spot.usecase.port.out.review.ReviewScrapRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewScrapService implements ReviewScrapUsecase {
    private final ReviewScrapRepository reviewScrapRepository;

    @Override
    public boolean toggleScrap(final long memberId, final long reviewId) {
        if (isScraped(memberId, reviewId)) {
            cancelScrap(memberId, reviewId);
            return false;
        }

        addScrap(memberId, reviewId);
        return true;
    }

    @Transactional(readOnly = true)
    public boolean isScraped(final long memberId, final long reviewId) {
        return reviewScrapRepository.existsBy(memberId, reviewId);
    }

    public void cancelScrap(final long memberId, final long reviewId) {
        reviewScrapRepository.deleteBy(memberId, reviewId);
    }

    public void addScrap(final long memberId, final long reviewId) {
        ReviewScrap like = ReviewScrap.builder().memberId(memberId).reviewId(reviewId).build();
        reviewScrapRepository.save(like);
    }
}
