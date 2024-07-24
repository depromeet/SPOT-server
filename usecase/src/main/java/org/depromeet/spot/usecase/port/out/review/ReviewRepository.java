package org.depromeet.spot.usecase.port.out.review;

import java.util.List;
import java.util.Optional;

import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.domain.review.ReviewYearMonth;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewRepository {
    Review save(Review review);

    Optional<Review> findById(Long id);

    long countByUserId(Long userId);

    Page<Review> findByStadiumIdAndBlockCode(
            Long stadiumId,
            String blockCode,
            Integer rowNumber,
            Integer seatNumber,
            Integer year,
            Integer month,
            Pageable pageable);

    Page<Review> findByUserId(Long userId, Integer year, Integer month, Pageable pageable);

    List<ReviewYearMonth> findReviewMonthsByMemberId(Long memberId);

    Long softDeleteByIdAndMemberId(Long reviewId, Long memberId);

    Review findLastReviewByMemberId(Long memberId);

    long countByIdByMemberId(Long memberId);
}
