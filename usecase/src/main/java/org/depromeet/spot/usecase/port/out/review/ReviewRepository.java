package org.depromeet.spot.usecase.port.out.review;

import java.util.List;

import org.depromeet.spot.domain.review.KeywordCount;
import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.domain.review.ReviewYearMonth;

public interface ReviewRepository {
    List<Review> findByBlockCode(
            Long stadiumId,
            String blockCode,
            Integer rowNumber,
            Integer seatNumber,
            int offset,
            int limit);

    List<Review> findByUserId(Long userId, int offset, int limit, Integer year, Integer month);

    Long countByBlockCode(Long stadiumId, String blockCode, Integer rowNumber, Integer seatNumber);

    Long countByUserId(Long userId, Integer year, Integer month);

    long countByUserId(Long userId);

    List<KeywordCount> findTopKeywordsByBlockCode(Long stadiumId, String blockCode, int limit);

    Review save(Review review);

    List<ReviewYearMonth> findReviewMonthsByMemberId(Long memberId);
}
