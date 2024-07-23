package org.depromeet.spot.usecase.port.in.review;

import java.util.List;

import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.domain.review.ReviewYearMonth;
import org.depromeet.spot.domain.review.image.TopReviewImage;
import org.springframework.data.domain.Pageable;

import lombok.Builder;

public interface ReadReviewUsecase {

    BlockReviewListResult findReviewsByStadiumIdAndBlockCode(
            Long stadiumId,
            String blockCode,
            Integer rowNumber,
            Integer seatNumber,
            Integer year,
            Integer month,
            Pageable pageable);

    MyReviewListResult findMyReviewsByUserId(
            Long userId, Integer year, Integer month, Pageable pageable);

    List<ReviewYearMonth> findReviewMonths(Long memberId);

    @Builder
    record BlockReviewListResult(
            List<Review> reviews,
            List<BlockKeywordInfo> topKeywords,
            List<TopReviewImage> topReviewImages,
            long totalElements,
            int totalPages,
            int number,
            int size) {}

    @Builder
    record BlockKeywordInfo(String content, Long count, Boolean isPositive) {}

    @Builder
    record MyReviewListResult(
            List<Review> reviews, long totalElements, int totalPages, int number, int size) {}
}
