package org.depromeet.spot.usecase.service.review;

import java.util.Collections;
import java.util.List;

import org.depromeet.spot.domain.review.KeywordCount;
import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.domain.review.ReviewListResult;
import org.depromeet.spot.usecase.port.in.review.ReviewReadUsecase;
import org.depromeet.spot.usecase.port.out.review.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewReadService implements ReviewReadUsecase {
    private final ReviewRepository reviewRepository;

    private static final int TOP_KEYWORDS_LIMIT = 5;

    @Override
    public ReviewListResult findReviewsByBlockId(
            Long stadiumId, Long blockId, Long rowId, Long seatNumber, int offset, int limit) {
        List<Review> reviews =
                reviewRepository.findByBlockId(
                        stadiumId, blockId, rowId, seatNumber, offset, limit);
        Long totalCount = reviewRepository.countByBlockId(stadiumId, blockId, rowId, seatNumber);
        List<KeywordCount> topKeywords =
                reviewRepository.findTopKeywordsByBlockId(stadiumId, blockId, TOP_KEYWORDS_LIMIT);

        return new ReviewListResult(reviews, topKeywords, totalCount, offset, limit);
    }

    @Override
    public ReviewListResult findMyReviews(
            Long userId, int offset, int limit, Integer year, Integer month) {
        List<Review> reviews = reviewRepository.findByUserId(userId, offset, limit, year, month);
        Long totalCount = reviewRepository.countByUserId(userId, year, month);
        List<KeywordCount> topKeywords = Collections.emptyList(); // 사용자 리뷰에 대한 탑 키워드 모음 필요 없음

        return new ReviewListResult(reviews, topKeywords, totalCount, offset, limit);
    }
}
