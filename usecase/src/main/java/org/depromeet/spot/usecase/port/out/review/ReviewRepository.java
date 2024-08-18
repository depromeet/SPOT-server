package org.depromeet.spot.usecase.port.out.review;

import java.util.List;
import java.util.Optional;

import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.domain.review.Review.SortCriteria;
import org.depromeet.spot.domain.review.ReviewYearMonth;
import org.depromeet.spot.usecase.port.in.review.ReadReviewUsecase.LocationInfo;

public interface ReviewRepository {
    Review save(Review review);

    Optional<Review> findById(Long id);

    long countByUserId(Long userId);

    List<Review> findByStadiumIdAndBlockCode(
            Long stadiumId,
            String blockCode,
            Integer rowNumber,
            Integer seatNumber,
            Integer year,
            Integer month,
            String cursor,
            SortCriteria sortBy,
            Integer size);

    List<Review> findAllByUserId(
            Long userId,
            Integer year,
            Integer month,
            String cursor,
            SortCriteria sortBy,
            Integer size);

    List<ReviewYearMonth> findReviewMonthsByMemberId(Long memberId);

    Long softDeleteByIdAndMemberId(Long reviewId, Long memberId);

    LocationInfo findLocationInfoByStadiumIdAndBlockCode(Long stadiumId, String blockCode);

    Review findLastReviewByMemberId(Long memberId);

    long countByIdByMemberId(Long memberId);
}
