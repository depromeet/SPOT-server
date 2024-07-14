package org.depromeet.spot.usecase.service.review;

import java.util.List;

import org.depromeet.spot.domain.review.KeywordCount;
import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.domain.review.ReviewListResult;
import org.depromeet.spot.usecase.port.in.review.ReviewReadUsecase;
import org.depromeet.spot.usecase.port.out.review.ReviewRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewReadService implements ReviewReadUsecase {
    private final ReviewRepository reviewRepository;

    @Override
    public ReviewListResult findReviewsByBlockId(
            Long stadiumId, Long blockId, Long rowId, Long seatNumber, int offset, int limit) {
        List<Review> reviews =
                reviewRepository.findByBlockId(
                        stadiumId, blockId, rowId, seatNumber, offset, limit);
        int totalCount = reviewRepository.countByBlockId(stadiumId, blockId, rowId, seatNumber);
        List<KeywordCount> topKeywords =
                reviewRepository.findTopKeywordsByBlockId(stadiumId, blockId, 5);

        return new ReviewListResult(reviews, topKeywords, totalCount, offset, limit);
    }
}
