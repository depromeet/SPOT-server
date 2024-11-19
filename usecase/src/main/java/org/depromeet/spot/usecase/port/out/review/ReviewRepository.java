package org.depromeet.spot.usecase.port.out.review;

import java.util.List;

import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.domain.review.Review.ReviewType;
import org.depromeet.spot.domain.review.Review.SortCriteria;
import org.depromeet.spot.domain.review.ReviewCount;
import org.depromeet.spot.domain.review.ReviewYearMonth;
import org.depromeet.spot.usecase.port.in.review.ReadReviewUsecase.LocationInfo;

public interface ReviewRepository {

    Review findReviewByIdWithLock(Long id);

    void updateLikesCount(Long reviewId, boolean isLiking);

    void updateScrapsCount(Long reviewId, int likesCount);

    Review save(Review review);

    Review findById(Long id);

    long countByUserId(Long userId);

    ReviewCount countAndSumLikesByUserId(Long id);

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
            Integer size,
            ReviewType reviewType);

    List<ReviewYearMonth> findReviewMonthsByMemberId(Long memberId, ReviewType reviewType);

    Long softDeleteByIdAndMemberId(Long reviewId, Long memberId);

    void softDeleteAllReviewOwnedByMemberId(Long memberId);

    LocationInfo findLocationInfoByStadiumIdAndBlockCode(Long stadiumId, String blockCode);

    Review findLastReviewByMemberId(Long memberId);

    long countByIdByMemberId(Long memberId);

    long countByStadiumIdAndBlockCode(
            Long stadiumId,
            String blockCode,
            Integer rowNumber,
            Integer seatNumber,
            Integer year,
            Integer month);
}
